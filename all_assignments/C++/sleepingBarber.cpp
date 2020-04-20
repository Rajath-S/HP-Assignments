//The analogy is based upon a hypothetical barber shop with one barber.
//There is a barber shop which has one barber, one barber chair, and n chairs for waiting for customers if there are any to sit on the chair.



#include <thread>
#include <iostream>
#include <chrono>
#include <condition_variable>
#include <vector>
#include <queue>
using namespace std;

//If there is no customer, then the barber sleeps in his own chair.
//When a customer arrives, he has to wake up the barber.
//If there are many customers and the barber is cutting a customer’s hair, then the remaining customers either wait if there are empty chairs in the waiting room or they leave if no chairs are empty.

condition_variable barber;
condition_variable cust;
mutex cust_count;
mutex operation;
int customer_count = 0;
int done_work = 0;
int num_chairs = 0;
int total_customers = 0;
queue<int> chairs;

void customer(int id){
    cust_count.lock();

    total_customers--;
    if (customer_count < num_chairs){
        
		//updating  counter and wait

        cout << "Customer " << id << " added to queue" << endl;
        customer_count++;
        chairs.push(id);
        if (total_customers == 0){
            done_work =1;

        }
        barber.notify_one();
    }else{
     cout << "Customer " << id << " left" << endl;
    }
    cust_count.unlock();
}
void barber_do(){
    while (done_work == 0){

        unique_lock<mutex> lock(operation);//obtain lock
        barber.wait(lock); //wait for notify here
        while(!chairs.empty()){
            cust_count.lock();
            customer_count--;
            //fininsh haircut
            cout << "finish hair cut for " << chairs.front() << endl;
            chairs.pop();
            cust_count.unlock();
        }
    }
}

int main(){
	cin >> num_chairs;
		int num_customers;
	cin >> num_customers;
    vector<thread> threads;
    thread barber_thread = thread(&barber_do);
    total_customers = num_customers;
    for (int i = 0; i < num_customers ;i++){
        threads.push_back(thread(&customer,i));
    }
    
    for(auto& customer_:threads){
        customer_.join();
    }
    barber_thread.join();
    return 0;
}