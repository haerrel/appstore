$apps = @(
    '{"text":"Zweite App","tags":"#2, #fancy","title":"Second App","price":2,"datePublished":"2019-04-30"}',
    '{"text":"Popularer Instant Messenger","tags":"#Whats, #UP","title":"WhatsApp","price":0,"datePublished":"2019-04-28"}'
)

$apps | %{
    Invoke-WebRequest -Method Post -Body $_ -Headers @{"Content-Type" = "application/json;charset=UTF-8"} http://localhost:8080/apps
}