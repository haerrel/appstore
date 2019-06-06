$apps = @(
    '{"text":"Zweite App","tags":"#2, #fancy","title":"Second App","price":2,"datePublished":"2019-04-30"}',
    '{"text":"Popularer Instant Messenger","tags":"#Whats, #UP","title":"WhatsApp","price":0,"datePublished":"2019-04-28"}'
    '{"text":"Whatever App","tags":"#Whats, #UP","title":"Wooooow","price":0,"datePublished":"2019-03-28"}'
    '{"text":"An Application with a too Big name","tags":"#fancy","title":"Application with an too long name","price":30,"datePublished":"2019-02-28"}'
    '{"text":"Lorem ipsum dolor sit amet","tags":"#Whats, #UP","title":"Lorem Ipsum","price":1000000,"datePublished":"2019-01-28"}'
)

$apps | %{
    Invoke-WebRequest -Method Post -Body $_ -Headers @{
        "Content-Type" = "application/json;charset=UTF-8";
        "Authorization" = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEZW1vRGV2ZWxvcGVyIiwicm9sZSI6IlJPTEVfREVWRUxPUEVSIiwiZXhwIjoxNTY0ODU5MzAzLCJpYXQiOjE1NTg4MTEzMDN9.YuATdHbQXBVicZTtt01vBB6CHof-D5e5SsS0pFI3714svh3yevfxavttKaNzm9Tt1Emuh90qZb6qbUoUxP-npw"
    } http://localhost:8080/apps
}