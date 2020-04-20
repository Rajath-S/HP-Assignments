import threading
import time
def do_something(name):
    count = 0
    while count < 5:
        time.sleep(10)
        count += 1
        print(name)

a = threading.Thread(None,do_something,"T1",("T1",))
b = threading.Thread(None,do_something,"T2",("T2",))
a.start()
b.start()