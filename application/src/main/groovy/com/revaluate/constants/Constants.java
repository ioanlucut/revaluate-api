package com.revaluate.constants;

public enum Constants
{
  ACCOUNT_LOGIN("Login", "account", "/login"),
  ACCOUNT_LOGOUT("Logout", "account", "/logout");

  private String name;
  private String base;
  private String path;

  Constants(final String newName, final String newBase, final String newPath)
  {
    this.name = newName;
    this.base = newBase;
    this.path = newPath;
  }

  public String getName()
  {
    return this.name;
  }

  public String getBase()
  {
    return this.base;
  }

  public String getPath()
  {
    return this.path;
  }
}