#Aloo App Backend

### Needed To Be Done For The Backend Side:
- [x] reset password things.
- [x] whole driver app.
- [x] rate things.
- [x] send SMS verifications.
- [x] restaurant offers things: offers will be added from database with all details (drinks, sizes, prices, etc.), and once the user click the offer will see it's details and add it to cart, and when the user make the order it will automatically fill everything.
- [x] Track Driver Time on every order.
- [x] Make The Price Things On Driver And Vendor Apps.
- [x] The Filters And Search in the list items and list vendors things

### Needed To Be Done On The Database Side:
- [x] rate migrations.
- [x] branch is available or not for getting orders.
- [x] every thing for review from customer.
- [x] migration for the (extras) in the item table (id, name_:lang,price,calories)

###order status 
- unconfirmed         ::::: 1
- confirmed By Vendor ::::: 2
- confirmed By driver
- driver waiting
- ready               ::::: 3
- in-delivery         ::::: 4
- driver arrived      ::::: 5
- delivered
- delivery confirmed  ::::: 6 
- done
- cancelled


### Publishing The App For Production
- ```composer install```.
- ```artisan vendor:publish ```  For DataTables Things (19-22).
