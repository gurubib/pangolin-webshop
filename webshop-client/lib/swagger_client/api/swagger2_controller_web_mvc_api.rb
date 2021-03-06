=begin
#OpenAPI definition

#No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)

OpenAPI spec version: v0

Generated by: https://github.com/swagger-api/swagger-codegen.git
Swagger Codegen version: 3.0.23
=end

module SwaggerClient
  class Swagger2ControllerWebMvcApi
    attr_accessor :api_client

    def initialize(api_client = ApiClient.default)
      @api_client = api_client
    end
    # @param [Hash] opts the optional parameters
    # @option opts [String] :group 
    # @return [String]
    def get_documentation1(opts = {})
      data, _status_code, _headers = get_documentation1_with_http_info(opts)
      data
    end

    # @param [Hash] opts the optional parameters
    # @option opts [String] :group 
    # @return [Array<(String, Integer, Hash)>] String data, response status code and response headers
    def get_documentation1_with_http_info(opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: Swagger2ControllerWebMvcApi.get_documentation1 ...'
      end
      # resource path
      local_var_path = '/v2/api-docs'

      # query parameters
      query_params = opts[:query_params] || {}
      query_params[:'group'] = opts[:'group'] if !opts[:'group'].nil?

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['application/json', 'application/hal+json'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] 

      return_type = opts[:return_type] || 'String' 

      auth_names = opts[:auth_names] || []
      data, status_code, headers = @api_client.call_api(:GET, local_var_path,
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type)

      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: Swagger2ControllerWebMvcApi#get_documentation1\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end
  end
end
