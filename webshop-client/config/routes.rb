Rails.application.routes.draw do
  root to: 'application#home', as: 'home'

  # Session paths
  get    'login'   => 'sessions#new'
  post   'login'   => 'sessions#create'
  get 'logout'  => 'sessions#destroy'
  get 'register' => 'users#new'
  post 'create' => 'users#create', as: 'create_user'

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
