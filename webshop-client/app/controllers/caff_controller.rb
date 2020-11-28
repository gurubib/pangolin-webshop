class CaffController < ApplicationController
  before_action :verify_user_logged_in
  def search
    @caff_files = []
    query = params[:query]
    if query == ""
      query = "caff"
    end
    token = session[:token]
    resp, header = @@api_user.search query, {:header_params => {"Authorization" => token}}
    session[:token] = header[:Authorization]
    if !resp.nil?
      @caff_files = resp.results
    end
  end

  def download
    token = session[:token]
    data, header = @@api_user.download params[:caff_uuid], {:header_params => {"Authorization" => token}}
    session[:token] = header[:Authorization]
    if !data.nil?
      caff_string = data.file_content_as_string
      caff = Base64.decode64 caff_string

      local_filename = "download" + session[:user_uuid] + ".caff"
      local_path = Rails.root.join('tmp', local_filename)

      File.open(local_path, 'wb') do |f|
        f.write(caff)
      end
      send_data(local_path, :filename => 'download.caff', :disposition => 'attachment')

      File.delete(local_path)
    end
  end

  def show
    @caff = nil
    filename = params[:caff_filename]
    token = session[:token]
    resp, header = @@api_user.search filename, {:header_params => {"Authorization" => token}}
    session[:token] = header[:Authorization]
    if !resp.nil?
      caff_hash = resp.results.first
      @caff = SwaggerClient::SearchResult.build_from_hash caff_hash
    end
  end

  private
  def verify_user_logged_in
    if session[:user_uuid].nil?
      flash[:error] = "Only logged in users can access this page!"
      redirect_back fallback_location: home_path
    end
  end
end
