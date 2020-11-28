module CaffHelper
  def prettify_comment_datetime datetime_str
    dt = DateTime.strptime datetime_str.partition(".").first, "%Y-%m-%dT%H:%M:%S"
    dt.strftime "%Y-%m-%d %H:%M"
  end
end
