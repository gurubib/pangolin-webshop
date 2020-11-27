class UsersController < ApplicationController
  before_action :verify_user_logged_in, except: [:new, :create]
  before_action :verify_is_admin, only: [:admin_page, :destroy, :edit_user, :update]

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
    @users = []
    token = session[:token]
    data, header = @@api_admin.list_users :header_params => {"Authorization" => token}
    session[:token] = header[:Authorization]
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

  def destroy
    user_uuid = params[:uuid]
    token = session[:token]
    data, header = @@api_admin.remove_user user_uuid, {:header_params => {"Authorization" => token}}
    session[:token] = header[:Authorization]
    redirect_back fallback_location: home_path
  end

  def edit_user
    user_hash = params[:selected_user].permit(:uuid, :username, :email, :dateOfBirth, :registrationDate).to_h
    @user = SwaggerClient::UserResult.build_from_hash user_hash
  end

  def update
    token = session[:token]
    req = SwaggerClient::UpdateUserRequest.new update_params
    logger.debug "Request (update): " + req.to_s
    data, header = @@api_admin.update_user req, params[:user][:uuid], {:header_params => {"Authorization" => token}}
    session[:token] = header[:Authorization]
    redirect_to admin_page_path
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

  def verify_is_admin
    @is_admin = session[:username] == "root"
    unless @is_admin
      flash[:error] = "Unauthorized access!"
      redirect_back fallback_location: home_path
    end
  end

  def update_params
    params.require(:user).permit(:email, :date_of_birth).to_h
  end
end
