class SessionsController < ApplicationController

  def new
  end

  def create
    req = SwaggerClient::LoginRequest.new session_params
    data = @@api_auth.login req
    if data.status == "SUCCESS"
      session[:user_uuid] = data.uuid
      session[:token] = data.token
      flash[:success] = "Successful login!"
      redirect_to home_path
    else
      flash[:error] = "Email or password is invalid!"
      redirect_back fallback_location: home_path
    end
  end

  def destroy
    reset_session
    flash[:success] = 'Successful logout!'
    redirect_to home_path
  end

  private
  def session_params
    params.require(:session).permit(:username, :password).to_h
  end
end
