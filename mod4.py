from urllib.request import urlopen
from bs4 import BeautifulSoup
import ssl

# Ignore SSL certificate errors
ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

url = 'http://py4e-data.dr-chuck.net/comments_2296473.html'
html = urlopen(url, context=ctx).read()
soup = BeautifulSoup(html, "html.parser")

# Retrieve all of the span tags
spans = soup('span')

count = len(spans)
sum_numbers = 0

for span in spans:
    sum_numbers += int(span.contents[0])

print('Count', count)
print('Sum', sum_numbers)
