#python client
import xmlrpclib, sys

if len(sys.argv) != 2:
    print "Usage: [hostname]"
    sys.exit()

hostname = sys.argv[1]
name = "http://"+hostname+ ":8084"
server = xmlrpclib.Server(name)
while True:
    command = input("Select operation: \n1)Lookup Book By Item Number\n2)Search For All Books By Topic\n3)Buy A Book By Item Number\n")
    if command == 1:
        item_num = input("Enter Item Number\n")
        book = server.FrontServer.lookup(int (item_num))
        
        print(book)

    elif command == 2:
        topic = input("Enter Topic\n")
        nums = server.FrontServer.search(topic)
        print(nums)
    elif command == 3:
        item_num = input("Enter Item Num\n")
        qty = server.FrontServer.buy(int (item_num))
        print(qty)
    else:
        print("Invalid command")
        


