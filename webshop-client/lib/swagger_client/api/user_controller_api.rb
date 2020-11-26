=begin
#OpenAPI definition

#No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)

OpenAPI spec version: v0

Generated by: https://github.com/swagger-api/swagger-codegen.git
Swagger Codegen version: 3.0.23
=end

module SwaggerClient
  class UserControllerApi
    attr_accessor :api_client

    def initialize(api_client = ApiClient.default)
      @api_client = api_client
    end
    # @param body 
    # @param uuid 
    # @param [Hash] opts the optional parameters
    # @return [Object]
    def add_comment(body, uuid, opts = {})
      data, _status_code, _headers = add_comment_with_http_info(body, uuid, opts)
      data
    end

    # @param body 
    # @param uuid 
    # @param [Hash] opts the optional parameters
    # @return [Array<(Object, Integer, Hash)>] Object data, response status code and response headers
    def add_comment_with_http_info(body, uuid, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: UserControllerApi.add_comment ...'
      end
      # verify the required parameter 'body' is set
      if @api_client.config.client_side_validation && body.nil?
        fail ArgumentError, "Missing the required parameter 'body' when calling UserControllerApi.add_comment"
      end
      # verify the required parameter 'uuid' is set
      if @api_client.config.client_side_validation && uuid.nil?
        fail ArgumentError, "Missing the required parameter 'uuid' when calling UserControllerApi.add_comment"
      end
      # resource path
      local_var_path = '/files/{uuid}/comments'.sub('{' + 'uuid' + '}', uuid.to_s)

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['*/*'])
      # HTTP header 'Content-Type'
      header_params['Content-Type'] = @api_client.select_header_content_type(['application/json'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] || @api_client.object_to_http_body(body) 

      return_type = opts[:return_type] || 'Object' 

      auth_names = opts[:auth_names] || []
      data, status_code, headers = @api_client.call_api(:POST, local_var_path,
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type)

      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: UserControllerApi#add_comment\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end
    # @param uuid 
    # @param [Hash] opts the optional parameters
    # @return [DownloadResponse]
    def download(uuid, opts = {})
      data, _status_code, _headers = download_with_http_info(uuid, opts)
      data
    end

    # @param uuid 
    # @param [Hash] opts the optional parameters
    # @return [Array<(DownloadResponse, Integer, Hash)>] DownloadResponse data, response status code and response headers
    def download_with_http_info(uuid, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: UserControllerApi.download ...'
      end
      # verify the required parameter 'uuid' is set
      if @api_client.config.client_side_validation && uuid.nil?
        fail ArgumentError, "Missing the required parameter 'uuid' when calling UserControllerApi.download"
      end
      # resource path
      local_var_path = '/files/{uuid}'.sub('{' + 'uuid' + '}', uuid.to_s)

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['application/json', '*/*'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] 

      return_type = opts[:return_type] || 'DownloadResponse' 

      auth_names = opts[:auth_names] || []
      data, status_code, headers = @api_client.call_api(:GET, local_var_path,
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type)

      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: UserControllerApi#download\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end
    # @param file_name 
    # @param [Hash] opts the optional parameters
    # @return [SearchResponse]
    def search(file_name, opts = {})
      data, _status_code, _headers = search_with_http_info(file_name, opts)
      data
    end

    # @param file_name 
    # @param [Hash] opts the optional parameters
    # @return [Array<(SearchResponse, Integer, Hash)>] SearchResponse data, response status code and response headers
    def search_with_http_info(file_name, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: UserControllerApi.search ...'
      end
      # verify the required parameter 'file_name' is set
      if @api_client.config.client_side_validation && file_name.nil?
        fail ArgumentError, "Missing the required parameter 'file_name' when calling UserControllerApi.search"
      end
      # resource path
      local_var_path = '/files/search/{fileName}'.sub('{' + 'fileName' + '}', file_name.to_s)

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['application/json', '*/*'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] 

      return_type = opts[:return_type] || 'SearchResponse' 

      auth_names = opts[:auth_names] || []
      data, status_code, headers = @api_client.call_api(:GET, local_var_path,
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type)

      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: UserControllerApi#search\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end
    # @param body 
    # @param [Hash] opts the optional parameters
    # @return [Object]
    def upload(body, opts = {})
      data, _status_code, _headers = upload_with_http_info(body, opts)
      data
    end

    # @param body 
    # @param [Hash] opts the optional parameters
    # @return [Array<(Object, Integer, Hash)>] Object data, response status code and response headers
    def upload_with_http_info(body, opts = {})
      if @api_client.config.debugging
        @api_client.config.logger.debug 'Calling API: UserControllerApi.upload ...'
      end
      # verify the required parameter 'body' is set
      if @api_client.config.client_side_validation && body.nil?
        fail ArgumentError, "Missing the required parameter 'body' when calling UserControllerApi.upload"
      end
      # resource path
      local_var_path = '/files'

      # query parameters
      query_params = opts[:query_params] || {}

      # header parameters
      header_params = opts[:header_params] || {}
      # HTTP header 'Accept' (if needed)
      header_params['Accept'] = @api_client.select_header_accept(['*/*'])
      # HTTP header 'Content-Type'
      header_params['Content-Type'] = @api_client.select_header_content_type(['application/json'])

      # form parameters
      form_params = opts[:form_params] || {}

      # http body (model)
      post_body = opts[:body] || @api_client.object_to_http_body(body) 

      return_type = opts[:return_type] || 'Object' 

      auth_names = opts[:auth_names] || []
      data, status_code, headers = @api_client.call_api(:POST, local_var_path,
        :header_params => header_params,
        :query_params => query_params,
        :form_params => form_params,
        :body => post_body,
        :auth_names => auth_names,
        :return_type => return_type)

      if @api_client.config.debugging
        @api_client.config.logger.debug "API called: UserControllerApi#upload\nData: #{data.inspect}\nStatus code: #{status_code}\nHeaders: #{headers}"
      end
      return data, status_code, headers
    end
  end
end
