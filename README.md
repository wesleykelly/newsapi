# News API Service
This project contains a search endpoint for articles from GNews along with query parameters including keyword and maximum responses, and more. It also includes caching.
# Example Curl
curl --location 'http://localhost:8080/api/search?q=technology&lang=en&country=us&max=5&in=title%2Cdescription&sortby=relevance'