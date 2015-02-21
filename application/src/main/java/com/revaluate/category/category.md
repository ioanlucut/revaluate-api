## CRUD
# login: "auth/login", -- YES
# create: "accounts/create/:email/:token", -- YES
# update: "accounts/update", -- YES
# details: "accounts/details", -- YES

## Update password
# updatePassword: "accounts/update_password",

## SIGN UP
# requestSignUpRegistration: "accounts/register_base"
# requestSignUpRegistration: "accounts/register"

## Password reset
# requestPasswordReset - sent from the form (with email in body)
# validatePasswordResetToken: "accounts/validate_password_reset_token/:email/:token" verifies if the token and email sent in the email match
# resetPasswordWithToken: "accounts/reset_password_with_token/:email/:token" - the real reset action having
        resetPasswordData.email, resetPasswordData.password, resetPasswordData.passwordConfirmation, resetPasswordData.token