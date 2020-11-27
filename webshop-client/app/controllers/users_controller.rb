class UsersController < ApplicationController
  before_action :verify_user_logged_in, except: [:new, :create]

  def new
  end

  def create
    req = SwaggerClient::RegistrationRequest.new user_params
    data = @@api_auth.register req
    if data.status == "SUCCESS"
      flash[:success] = "Successful registration!"
      redirect_to home_path
    else
      flash[:error] = "Something went wrong... Possible reasons: Username already in use / Passwords did not match"
      redirect_back fallback_location: home_path
    end
  end

  def upload_page
    @is_admin = session[:username] == "root"
  end

  def upload
    uploaded_file = params[:picture]
    filename = uploaded_file.original_filename
    file = uploaded_file.tempfile.open.read.force_encoding(Encoding::UTF_8)
    file_b64 = Base64.encode64(file)
    name = filename.partition(".").first
    extension = filename.partition(".").last.upcase

    req = SwaggerClient::UploadRequest.new Hash[:uploader_uuid => session[:user_uuid], :required_file_name => name,
                                                :file_content_as_string => file_b64, :file_type => extension]
    token = session[:token]
    data, header = @@api_user.upload req, {:header_params => {"Authorization" => token}}
    session[:token] = header[:Authorization]
    flash[:notice] = "File upload finished."
    redirect_to home_path
  end

  def admin_page
    @is_admin = session[:username] == "root"
    unless @is_admin
      flash[:error] = "Unauthorized access!"
      redirect_back fallback_location: home_path
    end

    @users = []
    token = session[:token]
    data, header = @@api_admin.list_users :header_params => {"Authorization" => token}
    session[:token] = header[:Authorization]
    logger.debug "---> " + data.to_s
    if !data.nil?
      filtered_users = data.users.select {|hash| hash[:username] != "root"}
      @users = filtered_users
    end
  end

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

  private
  # Only allow a list of trusted parameters through.
  def user_params
    params.require(:user).permit(:username, :email, :password, :password_confirm, :date_of_birth).to_h
  end

  def verify_user_logged_in
    if session[:user_uuid].nil?
      flash[:error] = "Only logged in users can access this page!"
      redirect_back fallback_location: home_path
    end
  end
end
