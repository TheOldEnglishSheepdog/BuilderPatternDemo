public class Student {

        // final instance fields
        private final int id;//final fields cannot change after assigned
        private final String name;
        private final String address;

        public Student(Builder builder)
        {
            this.id = builder.id;
            this.name = builder.name;
            this.address = builder.address;
        }

        // Static class Builder
        public static class Builder {

            /// instance fields
            private int id;
            private String name;
            private String address;

            public static Builder newInstance()
            {
                return new Builder();
            }
            //default constructor
            private Builder() {}

            // Setter methods
            public Builder setId(int id)
            {
                this.id = id;
                return this;
            }
            public Builder setName(String name)
            {
                this.name = name;
                return this;
            }
            public Builder setAddress(String address)
            {
                this.address = address;
                return this;
            }

            // build method to deal with outer class
            // to return outer instance
            public Student build()
            {
                return new Student(this);
            }
        }

        @Override
        public String toString()
        {
            return "id = " + this.id + ", name = " + this.name +
                    ", address = " + this.address;
        }
    }

    // Client Side Code
    class StudentReceiver {

        // volatile student instance to ensure visibility
        // of shared reference to immutable objects
        private volatile Student student;

        public StudentReceiver()
        {

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run()
                {//Builder is a static nested class, is should called by using the outer class Student
                    student = Student.Builder.newInstance()//create a builder instance,
                            .setId(1)//call builder's setter , to assign Builder's id
                            .setName("Ram")//call builder's setter , to assign Builder's name
                            .setAddress("Noida")//call builder's setter , to assign Builder's address
                            .build();//.build() is to create a OUtter class' instance and pass in the Builder instance that has been assigned values
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run()
                {
                    student = Student.Builder.newInstance()
                            .setId(2)
                            .setName("Shyam")
                            .setAddress("Delhi")
                            .build();//method chaining
                }
            });

            t1.start();
            t2.start();
        }

        public Student getStudent()
        {
            return student;
        }
    }
    
