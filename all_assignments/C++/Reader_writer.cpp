#include <iostream>
#include <thread>
#include <vector>
#include <queue>
#include <condition_variable>
#include <mutex>
//There is a shared resource which is accessed by multiple processes i.e.readers and writers.
//Any number of readers can read from the shared resource simultaneously, but only one writer can write to the shared resource at a time.
//When a writer is writing data to the resource, no other process can access the resource.
//A writer cannot write to the resource if there are any readers accessing the resource at that time.
//Similarly, a reader can not read if there is a writer accessing the resource or if there are any waiting writers.



using namespace std;

int readcount = 0;
mutex shvar_mutex; // shared variable mutex
mutex writing; //mutex for updating write_count
mutex read_mutex;//mutex for updating read_count
condition_variable wr;
int shared_var = 0;

void reader(int id) {
	read_mutex.lock();
	readcount++;
	if (readcount == 0) {
		//first reader cant read and should wait for writers
		unique_lock<mutex> lock(writing);
		read_mutex.unlock();
		wr.wait(lock);
	}
	else {
		read_mutex.unlock();
	}
	//perform read
	shvar_mutex.lock();
	cout << "Thread " << id << " reads " << shared_var << endl;
	shvar_mutex.unlock();
	read_mutex.lock();
	readcount--;
	if (readcount == 0) {
		//last reader
		wr.notify_one();
	}
	read_mutex.unlock();
}

void writer(int id) {
	unique_lock<mutex> lock(writing);
	wr.wait(lock);
	shvar_mutex.lock();
	shared_var = id;
	cout << "Shared var updated to " << id << endl;
	shvar_mutex.unlock();
	wr.notify_one();
}

int main(int argc, char ** argv) {
	cin >> num_readers;
	cin>> num_writers


	vector<thread> v;
	for (int i = 0; i < num_readers; i++) {
		v.push_back(thread(&reader, i));
	}
	for (int i = 0; i < num_writers;i++) {
		v.push_back(thread(&writer, i));
	}
	for (int i = num_readers; i < num_readers * 2; i++) {
		v.push_back(thread(&reader, i));
	}
	for (auto& th : v) {
		th.join();
	}
	return 0;
}