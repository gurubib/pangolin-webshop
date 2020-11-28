class ApplicationController < ActionController::Base
  layout 'home_page'
  require 'swagger_client'
  skip_before_action :verify_authenticity_token


  @@api_auth = SwaggerClient::AuthControllerApi.new
  @@api_user = SwaggerClient::UserControllerApi.new
  @@api_admin = SwaggerClient::AdminControllerApi.new
  # @@api_auth.api_client.config.debugging = true
  # @@api_user.api_client.config.debugging = true
  # @@api_admin.api_client.config.debugging = true

  def home
  end

end
