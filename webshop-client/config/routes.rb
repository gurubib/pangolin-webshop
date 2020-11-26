Rails.application.routes.draw do
  root to: 'application#home', as: 'home'

  # User paths
  get 'register' => 'users#new'
  post 'create' => 'users#create', as: 'create_user'
  get 'upload_page' => 'users#upload_page', as: 'upload_page'
  post 'upload' => 'users#upload', as: 'upload'

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
