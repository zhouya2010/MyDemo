// IStu.aidl
package app.example.com.mydemo.ipc;

// Declare any non-default types here with import statements
import app.example.com.mydemo.ipc.Stu;
interface IStu {
        void add(in Stu stu);
        void delete(in int id);
        List<Stu> query();
}
