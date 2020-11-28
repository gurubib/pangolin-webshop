Rails.application.routes.draw do
  root to: 'application#home', as: 'home'

  # User paths
  get 'register' => 'users#new'
  post 'create' => 'users#create', as: 'create_user'
  get 'upload_page' => 'users#upload_page', as: 'upload_page'
  post 'upload' => 'users#upload', as: 'upload'
  get 'admin_page' => 'users#admin_page', as: 'admin_page'

  delete 'delete' => 'users#destroy', as: 'delete_user'
  get 'edit_user' => 'users#edit_user', as: 'edit_user'
  post 'update_user' => 'users#update', as: 'update_user'


  # Caff paths
  get 'search' => 'caff#search', as: 'search'
  get 'download' => 'caff#download', as: 'download_caff'

  # Session paths
  get    'login'   => 'sessions#new'
  post   'login'   => 'sessions#create'
  get 'logout'  => 'sessions#destroy'


  # For details on the DSL available within this file, see https://guides.rubyonrails.org/routing.html
  get 'pages/home'
  get 'pages/log_in'
  get 'pages/register'
  get 'pages/show'
  get 'pages/upload'
  get 'pages/my_profile'
  get 'pages/admin_page'
  get 'pages/edit_profile'
end
