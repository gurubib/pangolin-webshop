=begin
#OpenAPI definition

#No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)

OpenAPI spec version: v0

Generated by: https://github.com/swagger-api/swagger-codegen.git
Swagger Codegen version: 3.0.23
=end

require 'date'

module SwaggerClient
  class SecurityConfiguration
    attr_accessor :api_key

    attr_accessor :api_key_vehicle

    attr_accessor :api_key_name

    attr_accessor :client_id

    attr_accessor :client_secret

    attr_accessor :realm

    attr_accessor :app_name

    attr_accessor :scope_separator

    attr_accessor :additional_query_string_params

    attr_accessor :use_basic_authentication_with_access_code_grant

    attr_accessor :enable_csrf_support

    # Attribute mapping from ruby-style variable name to JSON key.
    def self.attribute_map
      {
        :'api_key' => :'apiKey',
        :'api_key_vehicle' => :'apiKeyVehicle',
        :'api_key_name' => :'apiKeyName',
        :'client_id' => :'clientId',
        :'client_secret' => :'clientSecret',
        :'realm' => :'realm',
        :'app_name' => :'appName',
        :'scope_separator' => :'scopeSeparator',
        :'additional_query_string_params' => :'additionalQueryStringParams',
        :'use_basic_authentication_with_access_code_grant' => :'useBasicAuthenticationWithAccessCodeGrant',
        :'enable_csrf_support' => :'enableCsrfSupport'
      }
    end

    # Attribute type mapping.
    def self.openapi_types
      {
        :'api_key' => :'Object',
        :'api_key_vehicle' => :'Object',
        :'api_key_name' => :'Object',
        :'client_id' => :'Object',
        :'client_secret' => :'Object',
        :'realm' => :'Object',
        :'app_name' => :'Object',
        :'scope_separator' => :'Object',
        :'additional_query_string_params' => :'Object',
        :'use_basic_authentication_with_access_code_grant' => :'Object',
        :'enable_csrf_support' => :'Object'
      }
    end

    # List of attributes with nullable: true
    def self.openapi_nullable
      Set.new([
      ])
    end
  
    # Initializes the object
    # @param [Hash] attributes Model attributes in the form of hash
    def initialize(attributes = {})
      if (!attributes.is_a?(Hash))
        fail ArgumentError, "The input argument (attributes) must be a hash in `SwaggerClient::SecurityConfiguration` initialize method"
      end

      # check to see if the attribute exists and convert string to symbol for hash key
      attributes = attributes.each_with_object({}) { |(k, v), h|
        if (!self.class.attribute_map.key?(k.to_sym))
          fail ArgumentError, "`#{k}` is not a valid attribute in `SwaggerClient::SecurityConfiguration`. Please check the name to make sure it's valid. List of attributes: " + self.class.attribute_map.keys.inspect
        end
        h[k.to_sym] = v
      }

      if attributes.key?(:'api_key')
        self.api_key = attributes[:'api_key']
      end

      if attributes.key?(:'api_key_vehicle')
        self.api_key_vehicle = attributes[:'api_key_vehicle']
      end

      if attributes.key?(:'api_key_name')
        self.api_key_name = attributes[:'api_key_name']
      end

      if attributes.key?(:'client_id')
        self.client_id = attributes[:'client_id']
      end

      if attributes.key?(:'client_secret')
        self.client_secret = attributes[:'client_secret']
      end

      if attributes.key?(:'realm')
        self.realm = attributes[:'realm']
      end

      if attributes.key?(:'app_name')
        self.app_name = attributes[:'app_name']
      end

      if attributes.key?(:'scope_separator')
        self.scope_separator = attributes[:'scope_separator']
      end

      if attributes.key?(:'additional_query_string_params')
        if (value = attributes[:'additional_query_string_params']).is_a?(Hash)
          self.additional_query_string_params = value
        end
      end

      if attributes.key?(:'use_basic_authentication_with_access_code_grant')
        self.use_basic_authentication_with_access_code_grant = attributes[:'use_basic_authentication_with_access_code_grant']
      end

      if attributes.key?(:'enable_csrf_support')
        self.enable_csrf_support = attributes[:'enable_csrf_support']
      end
    end

    # Show invalid properties with the reasons. Usually used together with valid?
    # @return Array for valid properties with the reasons
    def list_invalid_properties
      invalid_properties = Array.new
      invalid_properties
    end

    # Check to see if the all the properties in the model are valid
    # @return true if the model is valid
    def valid?
      true
    end

    # Checks equality by comparing each attribute.
    # @param [Object] Object to be compared
    def ==(o)
      return true if self.equal?(o)
      self.class == o.class &&
          api_key == o.api_key &&
          api_key_vehicle == o.api_key_vehicle &&
          api_key_name == o.api_key_name &&
          client_id == o.client_id &&
          client_secret == o.client_secret &&
          realm == o.realm &&
          app_name == o.app_name &&
          scope_separator == o.scope_separator &&
          additional_query_string_params == o.additional_query_string_params &&
          use_basic_authentication_with_access_code_grant == o.use_basic_authentication_with_access_code_grant &&
          enable_csrf_support == o.enable_csrf_support
    end

    # @see the `==` method
    # @param [Object] Object to be compared
    def eql?(o)
      self == o
    end

    # Calculates hash code according to all attributes.
    # @return [Integer] Hash code
    def hash
      [api_key, api_key_vehicle, api_key_name, client_id, client_secret, realm, app_name, scope_separator, additional_query_string_params, use_basic_authentication_with_access_code_grant, enable_csrf_support].hash
    end

    # Builds the object from hash
    # @param [Hash] attributes Model attributes in the form of hash
    # @return [Object] Returns the model itself
    def self.build_from_hash(attributes)
      new.build_from_hash(attributes)
    end

    # Builds the object from hash
    # @param [Hash] attributes Model attributes in the form of hash
    # @return [Object] Returns the model itself
    def build_from_hash(attributes)
      return nil unless attributes.is_a?(Hash)
      self.class.openapi_types.each_pair do |key, type|
        if type =~ /\AArray<(.*)>/i
          # check to ensure the input is an array given that the attribute
          # is documented as an array but the input is not
          if attributes[self.class.attribute_map[key]].is_a?(Array)
            self.send("#{key}=", attributes[self.class.attribute_map[key]].map { |v| _deserialize($1, v) })
          end
        elsif !attributes[self.class.attribute_map[key]].nil?
          self.send("#{key}=", _deserialize(type, attributes[self.class.attribute_map[key]]))
        elsif attributes[self.class.attribute_map[key]].nil? && self.class.openapi_nullable.include?(key)
          self.send("#{key}=", nil)
        end
      end

      self
    end

    # Deserializes the data based on type
    # @param string type Data type
    # @param string value Value to be deserialized
    # @return [Object] Deserialized data
    def _deserialize(type, value)
      case type.to_sym
      when :DateTime
        DateTime.parse(value)
      when :Date
        Date.parse(value)
      when :String
        value.to_s
      when :Integer
        value.to_i
      when :Float
        value.to_f
      when :Boolean
        if value.to_s =~ /\A(true|t|yes|y|1)\z/i
          true
        else
          false
        end
      when :Object
        # generic object (usually a Hash), return directly
        value
      when /\AArray<(?<inner_type>.+)>\z/
        inner_type = Regexp.last_match[:inner_type]
        value.map { |v| _deserialize(inner_type, v) }
      when /\AHash<(?<k_type>.+?), (?<v_type>.+)>\z/
        k_type = Regexp.last_match[:k_type]
        v_type = Regexp.last_match[:v_type]
        {}.tap do |hash|
          value.each do |k, v|
            hash[_deserialize(k_type, k)] = _deserialize(v_type, v)
          end
        end
      else # model
        SwaggerClient.const_get(type).build_from_hash(value)
      end
    end

    # Returns the string representation of the object
    # @return [String] String presentation of the object
    def to_s
      to_hash.to_s
    end

    # to_body is an alias to to_hash (backward compatibility)
    # @return [Hash] Returns the object in the form of hash
    def to_body
      to_hash
    end

    # Returns the object in the form of hash
    # @return [Hash] Returns the object in the form of hash
    def to_hash
      hash = {}
      self.class.attribute_map.each_pair do |attr, param|
        value = self.send(attr)
        if value.nil?
          is_nullable = self.class.openapi_nullable.include?(attr)
          next if !is_nullable || (is_nullable && !instance_variable_defined?(:"@#{attr}"))
        end

        hash[param] = _to_hash(value)
      end
      hash
    end

    # Outputs non-array value in the form of hash
    # For object, use to_hash. Otherwise, just return the value
    # @param [Object] value Any valid value
    # @return [Hash] Returns the value in the form of hash
    def _to_hash(value)
      if value.is_a?(Array)
        value.compact.map { |v| _to_hash(v) }
      elsif value.is_a?(Hash)
        {}.tap do |hash|
          value.each { |k, v| hash[k] = _to_hash(v) }
        end
      elsif value.respond_to? :to_hash
        value.to_hash
      else
        value
      end
    end  end
end
