//============================================================================
// Name        : Bomber.cpp
// Author      : Alisson Oliveira
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
using namespace std;

class A {
public:
	virtual ~A() {}
	int x;
	virtual void print() {
		cout << x*x << endl;
	}
};

class B : public A {
public:
	void print() {
		cout << x << endl;
	}
};

int main() {
	A *b = new B();
	b->x = 2;
	b->print();
	return 0;
}
