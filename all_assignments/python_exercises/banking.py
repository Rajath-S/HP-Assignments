import threading
class Bank_Account(threading.Thread): 
    def __init__(self):
        self.balance=0
        print("Hello!!! Welcome to the Deposit & Withdrawal Machine") 
  
    def deposit(self,amount): 
        lock.acquire()
        self.balance += amount 
        lock.release()
        print("\n Amount Deposited:",amount) 
  
    def withdraw(self,amount): 
        lock.acquire()
        if self.balance>=amount: 
            self.balance-=amount 
            print("\n You Withdrew:", amount) 
        else: 
            print("\n Insufficient balance  ")
        lock.release() 
  
    def display(self): 
        print("\n Net Available Balance=",self.balance) 
  
   
# creating an object of class 
lock = threading.Lock()
s = Bank_Account() 
t1=threading.Thread(target=s.deposit,args=(100,))
t2=threading.Thread(target=s.withdraw,args=(20,))
t1.start()
t2.start()

t1.join()
t2.join() 
s.display() 