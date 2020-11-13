Rails.application.routes.draw do
  # For details on the DSL available within this file, see https://guides.rubyonrails.org/routing.html
  root to: 'pages#home'
  get 'pages/log_in'
  get 'pages/register'
  get 'pages/show'
  get 'pages/upload'
  get 'pages/my_profile'
  get 'pages/admin_page'
  get 'pages/edit_profile'
end
