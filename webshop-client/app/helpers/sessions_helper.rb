module SessionsHelper
  def logged_in?
    !session[:user_uuid].nil?
  end
end
