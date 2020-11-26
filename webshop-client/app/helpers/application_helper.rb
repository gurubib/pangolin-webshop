module ApplicationHelper
  def bulma_class_for flash_type
    case flash_type
    when "success"
      "is-success"
    when "error"
      "is-danger"
    when "alert"
      "is-warning"
    when "notice"
      "is-info"
    else
      "is-primary"
    end
  end
end
