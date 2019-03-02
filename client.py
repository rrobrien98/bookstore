#python client
# Bookstore Client
#Client specifies the hostname of the server they would like to request book info from
# Program will then instruct the client to choose an operation
# The client will select the operation by typing its corresponding number
# they will then be prompted to give further arguments

# The client can select book numbers 1001-1010, and the library has books of topics "Fantasy" "Romance" and "Mystery"


import xmlrpclib, sys

if len(sys.argv) != 2:
    print "Usage: [hostname]"
    sys.exit()

hostname = sys.argv[1]
name = "http://"+hostname+ ":8084"
server = xmlrpclib.Server(name)

#continuously ask for queries
while True:
    command = input("Select operation: \n1)Lookup Book By Item Number\n2)Search For All Books By Topic\n3)Buy A Book By Item Number\n")
    
    #process each command type differently
    if command == 1:
        item_num = input("Enter Item Number\n")
        book = server.FrontServer.lookup(int (item_num))
        
        if book[0] == "Cannot Find Book\n":
            print(book)
        else:
            book_data = book[0].split(",");
            print("Name: " + book_data[0] + "\nTopic: " + book_data[1] + "\nRemaining Copies: " + book_data[2])

    elif command == 2:
        topic = raw_input("Enter Topic\n")
        nums = server.FrontServer.search(topic)
        if nums[0] == -1:
            print("Cannot Find Book\n")
        else:    
            print("Book Nums of Matching Books\n")
            print(nums[0])

    elif command == 3:
        item_num = input("Enter Item Num\n")
        qty = server.FrontServer.buy(int (item_num))
        if qty[0] == -1:
            print("No More Copies\n")
        elif qty[0] == -2:
            print("Cannot Find Book\n")
        else:
            print("Purchase Success, Quantity of Item Remaining:\n")
            print(qty[0])
    else:
        print("Invalid command")
        


