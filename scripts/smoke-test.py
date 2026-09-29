#!/usr/bin/env python3
"""Exercise the local API with synthetic data; requires only Python's standard library."""

import argparse
import json
import sys
import time
import uuid
from http import HTTPStatus
from urllib.error import HTTPError, URLError
from urllib.parse import urlencode, urlsplit
from urllib.request import Request, urlopen


def request(base_url, method, path, payload=None, token=None, expected=HTTPStatus.OK):
    headers = {"Accept": "application/json"}
    if payload is not None:
        headers["Content-Type"] = "application/json"
    if token:
        headers["Authorization"] = "Bearer " + token
    data = json.dumps(payload).encode() if payload is not None else None
    try:
        response = urlopen(Request(base_url + path, data=data, headers=headers, method=method), timeout=10)
    except HTTPError as error:
        response = error
    with response:
        body = response.read().decode()
        if response.status != expected:
            raise RuntimeError(f"{method} {path}: expected {expected}, got {response.status}: {body}")
        result = json.loads(body) if body and "application/json" in response.headers.get("Content-Type", "") else body
        return result, response.headers


def check(condition, message):
    if not condition:
        raise RuntimeError(message)


def wait_for_api(base_url):
    deadline = time.monotonic() + 120
    last_error = None
    while time.monotonic() < deadline:
        try:
            body, _ = request(base_url, "GET", "/")
            check(body == {"answer": "OK"}, "Health endpoint did not return the expected OK response")
            return
        except (OSError, URLError, RuntimeError) as error:
            last_error = error
            time.sleep(1)
    raise RuntimeError(f"API did not become ready within 120 seconds: {last_error}")


def smoke_test(base_url):
    wait_for_api(base_url)
    print("PASS: API is ready", flush=True)
    request(base_url, "GET", "/expenses/retrieve", expected=HTTPStatus.UNAUTHORIZED)
    print("PASS: expenses require authentication", flush=True)

    credentials = {"email": f"smoke-{uuid.uuid4().hex}@example.com", "password": uuid.uuid4().hex}
    user, _ = request(base_url, "POST", "/account", {
        **credentials, "firstName": "Smoke", "lastName": "Test", "currency": {"currencyCode": "EUR"},
    })
    check(user.get("id", 0) > 0, "Sign-up did not return a user ID")
    token = None
    try:
        logged_in, headers = request(base_url, "POST", "/account/login", credentials)
        token = headers.get("AuthToken")
        check(bool(token), "Login did not return an AuthToken header")
        check(logged_in["id"] == user["id"], "Login returned a different user")
        print("PASS: sign-up and login", flush=True)

        config, _ = request(base_url, "GET", "/appconfig/fetchConfig")
        category, _ = request(base_url, "POST", "/categories", {
            "name": "FOOD", "color": config["ALL_COLORS"][0],
        }, token)
        check(category.get("id", 0) > 0, "Category creation did not return an ID")
        expense, _ = request(base_url, "POST", "/expenses", {
            "value": 23.5, "description": "Smoke test lunch", "category": category,
            "spentDate": "2026-09-02T12:00:00.000",
        }, token)
        check(expense.get("id", 0) > 0, "Expense creation did not return an ID")
        expenses, _ = request(base_url, "GET", "/expenses/retrieve", token=token)
        check(len(expenses) == 1, "Expected exactly one expense for the new user")
        saved = expenses[0]
        check(saved["id"] == expense["id"] and saved["value"] == 23.5
              and saved["description"] == "Smoke test lunch" and saved["category"]["id"] == category["id"],
              "Retrieved expense does not match the saved expense")
        print("PASS: category and expense creation, persisted expense retrieval", flush=True)

        period = urlencode({"from": "2026-09-01T00:00:00Z", "to": "2026-10-01T00:00:00Z"})
        insights, _ = request(base_url, "GET", "/insights/retrieve_from_to?" + period, token=token)
        check(insights["numberOfTransactions"] == 1 and insights["totalAmountSpent"] == 23.5,
              "Monthly insights do not reflect the saved expense")
        print("PASS: monthly insights report one expense totalling 23.50", flush=True)
    finally:
        if token:
            request(base_url, "DELETE", "/account", token=token)
            print("PASS: temporary account removed", flush=True)
        else:
            print(f"Cleanup unavailable: remove temporary account {credentials['email']} or discard the test database.", file=sys.stderr)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--base-url", default="http://localhost:8080")
    args = parser.parse_args()
    url = urlsplit(args.base_url)
    if url.scheme != "http" or url.hostname not in ("localhost", "127.0.0.1", "::1") or url.username or url.password or url.query or url.fragment or url.path not in ("", "/"):
        parser.error("Use a local HTTP origin, e.g. http://localhost:8080; do not run against production.")
    try:
        smoke_test(args.base_url.rstrip("/"))
    except (OSError, URLError, RuntimeError, ValueError, KeyError, IndexError, TypeError) as error:
        print(f"FAIL: {error}", file=sys.stderr)
        return 1
    print("Smoke test passed.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
