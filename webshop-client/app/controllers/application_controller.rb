class ApplicationController < ActionController::Base
  layout 'home_page'
  require 'swagger_client'

  @@api_auth = SwaggerClient::AuthControllerApi.new
  @@api_user = SwaggerClient::UserControllerApi.new
  @@api_auth.api_client.config.debugging = true
  @@api_user.api_client.config.debugging = true

  def home
  end

end
