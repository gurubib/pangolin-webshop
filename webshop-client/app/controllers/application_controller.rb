class ApplicationController < ActionController::Base
  layout 'home_page'
  require 'swagger_client'

  @@api_auth = SwaggerClient::AuthControllerApi.new
  @@api_user = SwaggerClient::UserControllerApi.new
  def home
  end
end
