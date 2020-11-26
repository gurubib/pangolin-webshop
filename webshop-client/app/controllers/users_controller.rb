class UsersController < ApplicationController

  def new
  end

  def create
    req = SwaggerClient::RegistrationRequest.new user_params
    data = @@api_auth.register req
    if data.status == "SUCCESS"
      flash[:success] = "Successful registration!"
      redirect_to home_path
    else
      flash[:error] = "Something went wrong... Possible reasons: Username already in use / Passwords did not match"
      redirect_back fallback_location: home_path
    end
  end

  private
  # Only allow a list of trusted parameters through.
  def user_params
    params.require(:user).permit(:username, :email, :password, :password_confirm, :date_of_birth).to_h
  end
end
