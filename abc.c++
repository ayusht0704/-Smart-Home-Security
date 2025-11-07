#include <iostream>
#include <string>
using namespace std;

// Structure for patient (used in Queue)
struct Patient {
    int id;
    string name;
    string ailment;
    Patient* next;
};

// Structure for doctor (used in Linked List)
struct Doctor {
    int id;
    string name;
    string specialty;
    Doctor* next;
};

// Queue class for handling patients
class PatientQueue {
private:
    Patient* front;
    Patient* rear;
    int nextPatientId;

public:
    PatientQueue() {
        front = rear = nullptr;
        nextPatientId = 1;
    }

    void enqueuePatient() {
        string name, ailment;
        cout << "Enter patient name: ";
        getline(cin, name);
        cout << "Enter ailment: ";
        getline(cin, ailment);

        Patient* newPatient = new Patient{ nextPatientId++, name, ailment, nullptr };

        if (!rear) {
            front = rear = newPatient;
        } else {
            rear->next = newPatient;
            rear = newPatient;
        }

        cout << "Patient added successfully. ID: " << newPatient->id << endl;
    }

    void servePatient() {
        if (!front) {
            cout << "No patients in queue.\n";
            return;
        }

        Patient* temp = front;
        front = front->next;

        if (!front) rear = nullptr;

        cout << "Serving patient ID: " << temp->id << " | Name: " << temp->name
             << " | Ailment: " << temp->ailment << endl;
        delete temp;
    }

    void displayQueue() {
        if (!front) {
            cout << "No patients in queue.\n";
            return;
        }

        cout << "\nCurrent Patient Queue:\n";
        Patient* temp = front;
        while (temp) {
            cout << "  ID: " << temp->id << " | " << temp->name << " | " << temp->ailment << endl;
            temp = temp->next;
        }
    }

    ~PatientQueue() {
        while (front) {
            Patient* temp = front;
            front = front->next;
            delete temp;
        }
    }
};

// Linked List class for handling doctors
class DoctorList {
private:
    Doctor* head;
    int nextDoctorId;

public:
    DoctorList() {
        head = nullptr;
        nextDoctorId = 1;
    }

    void addDoctor() {
        string name, specialty;
        cout << "Enter doctor's name: ";
        getline(cin, name);
        cout << "Enter specialty: ";
        getline(cin, specialty);

        Doctor* newDoc = new Doctor{ nextDoctorId++, name, specialty, nullptr };

        if (!head) {
            head = newDoc;
        } else {
            Doctor* temp = head;
            while (temp->next) temp = temp->next;
            temp->next = newDoc;
        }

        cout << "Doctor added successfully. ID: " << newDoc->id << endl;
    }

    void displayDoctors() {
        if (!head) {
            cout << "No doctors in schedule.\n";
            return;
        }

        cout << "\nDoctor Schedule List:\n";
        Doctor* temp = head;
        while (temp) {
            cout << "  ID: " << temp->id << " | " << temp->name
                 << " | Specialty: " << temp->specialty << endl;
            temp = temp->next;
        }
    }

    void findDoctorById() {
        if (!head) {
            cout << "Doctor list is empty.\n";
            return;
        }

        int id;
        cout << "Enter Doctor ID to find: ";
        cin >> id;
        cin.ignore();

        Doctor* temp = head;
        while (temp) {
            if (temp->id == id) {
                cout << "Found: ID " << temp->id << " | " << temp->name
                     << " | Specialty: " << temp->specialty << endl;
                return;
            }
            temp = temp->next;
        }

        cout << "Doctor not found.\n";
    }

    ~DoctorList() {
        while (head) {
            Doctor* temp = head;
            head = head->next;
            delete temp;
        }
    }
};

// Main Program
int main() {
    PatientQueue queue;
    DoctorList doctors;
    int choice;

    while (true) {
        cout << "\n--- Hospital Queue Manager ---\n";
        cout << "1. Add Patient to Queue\n";
        cout << "2. Serve Next Patient\n";
        cout << "3. View Patient Queue\n";
        cout << "4. Add Doctor to Schedule\n";
        cout << "5. View Doctor Schedule\n";
        cout << "6. Find Doctor by ID\n";
        cout << "0. Exit\n";
        cout << "Enter choice: ";
        cin >> choice;
        cin.ignore();

        switch (choice) {
            case 1: queue.enqueuePatient(); break;
            case 2: queue.servePatient(); break;
            case 3: queue.displayQueue(); break;
            case 4: doctors.addDoctor(); break;
            case 5: doctors.displayDoctors(); break;
            case 6: doctors.findDoctorById(); break;
            case 0: cout << "Exiting...\n"; return 0;
            default: cout << "Invalid choice! Try again.\n"; break;
        }
    }
}
