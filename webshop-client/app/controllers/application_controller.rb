class ApplicationController < ActionController::Base
  layout 'home_page'
  require 'swagger_client'

  @@api_auth = SwaggerClient::AuthControllerApi.new

  def home
  end
end
