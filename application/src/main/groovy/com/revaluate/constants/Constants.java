package com.revaluate.constants;

public enum Constants
{
  ACCOUNT_ACCOUNT("Account", "account", "", "account"),
  ACCOUNT_IS_UNIQUE_EMAIL("Is unique email", "account", "/isUniqueEmail", "account/isUniqueEmail"),
  ACCOUNT_CREATE("Create", "account", "/create", "account/create"),
  ACCOUNT_LOGIN("Login", "account", "/login", "account/login"),
  ACCOUNT_DETAILS("Details", "account", "/details", "account/details"),
  ACCOUNT_UPDATE("Update", "account", "/update", "account/update"),
  ACCOUNT_CANCEL("Cancel", "account", "/remove", "account/remove"),
  ACCOUNT_UPDATE_PASSWORD("Update password", "account", "/updatePassword", "account/updatePassword"),
  ACCOUNT_REQUEST_RESET_PASSWORD("Request reset password", "account", "/requestResetPassword/{email}", "account/requestResetPassword/{email}"),
  ACCOUNT_VALIDATE_RESET_PASSWORD_TOKEN("Validate reset password token", "account", "/validateResetPasswordToken/{email}/{token}", "account/validateResetPasswordToken/{email}/{token}"),
  ACCOUNT_RESET_PASSWORD("Reset password", "account", "/resetPassword/{email}/{token}", "account/resetPassword/{email}/{token}");

  public final String name;
  public final String base;
  public final String sub;
  public final String uri;

  Constants(final String newName, final String newBase, final String newSub, final String newUri)
  {
    this.name = newName;
    this.base = newBase;
    this.sub = newSub;
    this.uri = newUri;
  }

}