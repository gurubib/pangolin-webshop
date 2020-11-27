=begin
#OpenAPI definition

#No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)

OpenAPI spec version: v0

Generated by: https://github.com/swagger-api/swagger-codegen.git
Swagger Codegen version: 3.0.23
=end

require 'date'

module SwaggerClient
  class UiConfiguration
    attr_accessor :deep_linking

    attr_accessor :display_operation_id

    attr_accessor :default_models_expand_depth

    attr_accessor :default_model_expand_depth

    attr_accessor :default_model_rendering

    attr_accessor :display_request_duration

    attr_accessor :doc_expansion

    attr_accessor :filter

    attr_accessor :max_displayed_tags

    attr_accessor :operations_sorter

    attr_accessor :show_extensions

    attr_accessor :show_common_extensions

    attr_accessor :tags_sorter

    attr_accessor :validator_url

    attr_accessor :supported_submit_methods

    attr_accessor :swagger_base_ui_url

    class EnumAttributeValidator
      attr_reader :datatype
      attr_reader :allowable_values

      def initialize(datatype, allowable_values)
        @allowable_values = allowable_values.map do |value|
          case datatype.to_s
          when /Integer/i
            value.to_i
          when /Float/i
            value.to_f
          else
            value
          end
        end
      end

      def valid?(value)
        !value || allowable_values.include?(value)
      end
    end

    # Attribute mapping from ruby-style variable name to JSON key.
    def self.attribute_map
      {
        :'deep_linking' => :'deepLinking',
        :'display_operation_id' => :'displayOperationId',
        :'default_models_expand_depth' => :'defaultModelsExpandDepth',
        :'default_model_expand_depth' => :'defaultModelExpandDepth',
        :'default_model_rendering' => :'defaultModelRendering',
        :'display_request_duration' => :'displayRequestDuration',
        :'doc_expansion' => :'docExpansion',
        :'filter' => :'filter',
        :'max_displayed_tags' => :'maxDisplayedTags',
        :'operations_sorter' => :'operationsSorter',
        :'show_extensions' => :'showExtensions',
        :'show_common_extensions' => :'showCommonExtensions',
        :'tags_sorter' => :'tagsSorter',
        :'validator_url' => :'validatorUrl',
        :'supported_submit_methods' => :'supportedSubmitMethods',
        :'swagger_base_ui_url' => :'swaggerBaseUiUrl'
      }
    end

    # Attribute type mapping.
    def self.openapi_types
      {
        :'deep_linking' => :'Object',
        :'display_operation_id' => :'Object',
        :'default_models_expand_depth' => :'Object',
        :'default_model_expand_depth' => :'Object',
        :'default_model_rendering' => :'Object',
        :'display_request_duration' => :'Object',
        :'doc_expansion' => :'Object',
        :'filter' => :'Object',
        :'max_displayed_tags' => :'Object',
        :'operations_sorter' => :'Object',
        :'show_extensions' => :'Object',
        :'show_common_extensions' => :'Object',
        :'tags_sorter' => :'Object',
        :'validator_url' => :'Object',
        :'supported_submit_methods' => :'Object',
        :'swagger_base_ui_url' => :'Object'
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
        fail ArgumentError, "The input argument (attributes) must be a hash in `SwaggerClient::UiConfiguration` initialize method"
      end

      # check to see if the attribute exists and convert string to symbol for hash key
      attributes = attributes.each_with_object({}) { |(k, v), h|
        if (!self.class.attribute_map.key?(k.to_sym))
          fail ArgumentError, "`#{k}` is not a valid attribute in `SwaggerClient::UiConfiguration`. Please check the name to make sure it's valid. List of attributes: " + self.class.attribute_map.keys.inspect
        end
        h[k.to_sym] = v
      }

      if attributes.key?(:'deep_linking')
        self.deep_linking = attributes[:'deep_linking']
      end

      if attributes.key?(:'display_operation_id')
        self.display_operation_id = attributes[:'display_operation_id']
      end

      if attributes.key?(:'default_models_expand_depth')
        self.default_models_expand_depth = attributes[:'default_models_expand_depth']
      end

      if attributes.key?(:'default_model_expand_depth')
        self.default_model_expand_depth = attributes[:'default_model_expand_depth']
      end

      if attributes.key?(:'default_model_rendering')
        self.default_model_rendering = attributes[:'default_model_rendering']
      end

      if attributes.key?(:'display_request_duration')
        self.display_request_duration = attributes[:'display_request_duration']
      end

      if attributes.key?(:'doc_expansion')
        self.doc_expansion = attributes[:'doc_expansion']
      end

      if attributes.key?(:'filter')
        self.filter = attributes[:'filter']
      end

      if attributes.key?(:'max_displayed_tags')
        self.max_displayed_tags = attributes[:'max_displayed_tags']
      end

      if attributes.key?(:'operations_sorter')
        self.operations_sorter = attributes[:'operations_sorter']
      end

      if attributes.key?(:'show_extensions')
        self.show_extensions = attributes[:'show_extensions']
      end

      if attributes.key?(:'show_common_extensions')
        self.show_common_extensions = attributes[:'show_common_extensions']
      end

      if attributes.key?(:'tags_sorter')
        self.tags_sorter = attributes[:'tags_sorter']
      end

      if attributes.key?(:'validator_url')
        self.validator_url = attributes[:'validator_url']
      end

      if attributes.key?(:'supported_submit_methods')
        if (value = attributes[:'supported_submit_methods']).is_a?(Array)
          self.supported_submit_methods = value
        end
      end

      if attributes.key?(:'swagger_base_ui_url')
        self.swagger_base_ui_url = attributes[:'swagger_base_ui_url']
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
      default_model_rendering_validator = EnumAttributeValidator.new('Object', ['example', 'model'])
      return false unless default_model_rendering_validator.valid?(@default_model_rendering)
      doc_expansion_validator = EnumAttributeValidator.new('Object', ['none', 'list', 'full'])
      return false unless doc_expansion_validator.valid?(@doc_expansion)
      operations_sorter_validator = EnumAttributeValidator.new('Object', ['alpha', 'method'])
      return false unless operations_sorter_validator.valid?(@operations_sorter)
      tags_sorter_validator = EnumAttributeValidator.new('Object', ['alpha'])
      return false unless tags_sorter_validator.valid?(@tags_sorter)
      true
    end

    # Custom attribute writer method checking allowed values (enum).
    # @param [Object] default_model_rendering Object to be assigned
    def default_model_rendering=(default_model_rendering)
      validator = EnumAttributeValidator.new('Object', ['example', 'model'])
      unless validator.valid?(default_model_rendering)
        fail ArgumentError, "invalid value for \"default_model_rendering\", must be one of #{validator.allowable_values}."
      end
      @default_model_rendering = default_model_rendering
    end

    # Custom attribute writer method checking allowed values (enum).
    # @param [Object] doc_expansion Object to be assigned
    def doc_expansion=(doc_expansion)
      validator = EnumAttributeValidator.new('Object', ['none', 'list', 'full'])
      unless validator.valid?(doc_expansion)
        fail ArgumentError, "invalid value for \"doc_expansion\", must be one of #{validator.allowable_values}."
      end
      @doc_expansion = doc_expansion
    end

    # Custom attribute writer method checking allowed values (enum).
    # @param [Object] operations_sorter Object to be assigned
    def operations_sorter=(operations_sorter)
      validator = EnumAttributeValidator.new('Object', ['alpha', 'method'])
      unless validator.valid?(operations_sorter)
        fail ArgumentError, "invalid value for \"operations_sorter\", must be one of #{validator.allowable_values}."
      end
      @operations_sorter = operations_sorter
    end

    # Custom attribute writer method checking allowed values (enum).
    # @param [Object] tags_sorter Object to be assigned
    def tags_sorter=(tags_sorter)
      validator = EnumAttributeValidator.new('Object', ['alpha'])
      unless validator.valid?(tags_sorter)
        fail ArgumentError, "invalid value for \"tags_sorter\", must be one of #{validator.allowable_values}."
      end
      @tags_sorter = tags_sorter
    end

    # Checks equality by comparing each attribute.
    # @param [Object] Object to be compared
    def ==(o)
      return true if self.equal?(o)
      self.class == o.class &&
          deep_linking == o.deep_linking &&
          display_operation_id == o.display_operation_id &&
          default_models_expand_depth == o.default_models_expand_depth &&
          default_model_expand_depth == o.default_model_expand_depth &&
          default_model_rendering == o.default_model_rendering &&
          display_request_duration == o.display_request_duration &&
          doc_expansion == o.doc_expansion &&
          filter == o.filter &&
          max_displayed_tags == o.max_displayed_tags &&
          operations_sorter == o.operations_sorter &&
          show_extensions == o.show_extensions &&
          show_common_extensions == o.show_common_extensions &&
          tags_sorter == o.tags_sorter &&
          validator_url == o.validator_url &&
          supported_submit_methods == o.supported_submit_methods &&
          swagger_base_ui_url == o.swagger_base_ui_url
    end

    # @see the `==` method
    # @param [Object] Object to be compared
    def eql?(o)
      self == o
    end

    # Calculates hash code according to all attributes.
    # @return [Integer] Hash code
    def hash
      [deep_linking, display_operation_id, default_models_expand_depth, default_model_expand_depth, default_model_rendering, display_request_duration, doc_expansion, filter, max_displayed_tags, operations_sorter, show_extensions, show_common_extensions, tags_sorter, validator_url, supported_submit_methods, swagger_base_ui_url].hash
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