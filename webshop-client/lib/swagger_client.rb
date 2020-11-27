=begin
#OpenAPI definition

#No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)

OpenAPI spec version: v0

Generated by: https://github.com/swagger-api/swagger-codegen.git
Swagger Codegen version: 3.0.23
=end

# Common files
require 'swagger_client/api_client'
require 'swagger_client/api_error'
require 'swagger_client/version'
require 'swagger_client/configuration'

# Models
require 'swagger_client/models/add_comment_request'
require 'swagger_client/models/comment_result'
require 'swagger_client/models/download_response'
require 'swagger_client/models/login_request'
require 'swagger_client/models/login_response'
require 'swagger_client/models/registration_request'
require 'swagger_client/models/registration_response'
require 'swagger_client/models/search_response'
require 'swagger_client/models/search_result'
require 'swagger_client/models/security_configuration'
require 'swagger_client/models/swagger_resource'
require 'swagger_client/models/ui_configuration'
require 'swagger_client/models/update_user_request'
require 'swagger_client/models/upload_request'

# APIs
require 'swagger_client/api/admin_controller_api'
require 'swagger_client/api/api_resource_controller_api'
require 'swagger_client/api/auth_controller_api'
require 'swagger_client/api/open_api_controller_web_mvc_api'
require 'swagger_client/api/swagger2_controller_web_mvc_api'
require 'swagger_client/api/user_controller_api'

module SwaggerClient
  class << self
    # Customize default settings for the SDK using block.
    #   SwaggerClient.configure do |config|
    #     config.username = "xxx"
    #     config.password = "xxx"
    #   end
    # If no block given, return the default Configuration object.
    def configure
      if block_given?
        yield(Configuration.default)
      else
        Configuration.default
      end
    end
  end
end