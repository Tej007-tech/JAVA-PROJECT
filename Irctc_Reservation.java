import java.util.*;

class Train {
    String[] stationName;
    int[] distance;
    String trainName;
    int trainNo;

    public Train(String trainName, int trainNo, String[] stationName, int[] distance) {
        this.stationName = stationName;
        this.distance = distance;
        this.trainName = trainName;
        this.trainNo = trainNo;
    }
}

class BookingSystem {
    public Train[] trains;
    public int trainIndex;
    public int fromIndex;
    public int toIndex;

    public BookingSystem() {
        
        trains = new Train[2];

        String[] stations1 = { "RAJKOT", "WANKANER", "SURENDRANAGAR", "VIRAMGAM", "AHMEDABAD", "NADIAD", "ANAND",
                "VADODARA", "ANKLESHWAR", "SURAT", "NAVSARI", "VALSAD", "VAPI", "VASAI", "BHIWANDI", "KALYAN",
                "LONAVALA", "PUNE", "DAUND", "SOLAPUR", "KALABURAGI", "WADI", "CHITTAPUR", "SEDAM", "TANDUR",
                "BEGAMPET", "SECUNDERABAD" };
        int[] kms1 = { 0, 42, 116, 181, 246, 292, 310, 344, 424, 474, 503, 542, 568, 689, 716, 731, 806, 869, 947, 1135,
                1248, 1284, 1299, 1322, 1355, 1465, 1470 };
        trains[0] = new Train("RJT SC SUP EXP", 22717, stations1, kms1);

        String[] stations2 = { "JAMNAGAR", "HAPA", "JAM WANTHALI", "RAJKOT", "WANKANER JN", "THAN JN", "SURENDRANAGAR",
                "VIRAMGAM", "AMBLI ROAD", "CHANDLODIYA", "SABARMATI JN", "AHMEDABAD JN", "NADIAD JN", "ANAND JN",
                "VADODARA JN" };
        int[] kms2 = { 0, 9, 30, 82, 123, 150, 198, 262, 311, 317, 322, 328, 373, 392, 426 };
        trains[1] = new Train("INTERCITY EXP", 22960, stations2, kms2);
    }

    public boolean findTrain(String fromStation, String toStation) {
        fromIndex = -1;
        toIndex = -1;
        trainIndex = -1;

        for (int i = 0; i < trains.length; i++) {
            for (int j = 0; j < trains[i].stationName.length; j++) {
                if (trains[i].stationName[j].equalsIgnoreCase(fromStation)) {
                    fromIndex = j;
                }
                if (fromIndex != -1 && trains[i].stationName[j].equalsIgnoreCase(toStation)) {
                    toIndex = j;
                    trainIndex = i;
                    System.out.println("Train Found: " + trains[i].trainNo + " " + trains[i].trainName);
                    return true;
                }
            }
        }
        return false;
    }

    public int getFromIndex() { return fromIndex; }
    public int getToIndex() { return toIndex; }
    public Train getSelectedTrain() { return trainIndex >= 0 ? trains[trainIndex] : null; }
}

class CoachDetails {
    int[] seats = {72, 72, 72, 48, 24};

    public void coachBooking(BookingSystem bookingSystem) {
        Scanner sc=new Scanner(System.in);

        Train train = bookingSystem.getSelectedTrain();
        if (train == null) {
            System.out.println("No valid train found.");
            return;
        }
        
        try {
            System.out.println("presh S for Sleeper (SL)\npresh B for 3 Tier AC (3A)\npresh A for 2 Tier AC (2A)\npresh H for 1st class AC (1A)");
            char coach = sc.nextLine().toUpperCase().charAt(0);
            System.out.println("No. of passengers: ");
            int passengers = sc.nextInt();

            if(passengers<0){
                System.out.println("Please enter positive value of passengers");
                return;
            }

            int fromIndex = bookingSystem.getFromIndex();
            int toIndex = bookingSystem.getToIndex();
            int distance = train.distance[toIndex] - train.distance[fromIndex];
            float price = 0;

            switch (coach) {
                case 'S':
                    price = processBooking(seats, 0, passengers, distance, 1);
                    break;
                case 'B':
                    price = processBooking(seats, 2, passengers, distance, 2);
                    break;
                case 'A':
                    price = processBooking(seats, 3, passengers, distance, 3);
                    break;
                case 'H':
                    price = processBooking(seats, 4, passengers, distance, 4);
                    break;
                default:
                    System.out.println("Enter a valid coach type.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter correct data type.");
            sc.next();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public float processBooking(int[] seats, int index, int passengers, int distance, int rate) {
        if (seats[index] >= passengers) {
            float price = distance * passengers * rate;
            seats[index] -= passengers;
            System.out.println("Booking successful. Total price: " + price);
            return price;
        } else {
            System.out.println("Not enough seats available.");
            return 0;
        }
    }
}

public class Irctc_Reservation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingSystem bookingSystem = new BookingSystem();
        
        try {
            System.out.println("Enter From Station: ");
            String fromStation = sc.nextLine().trim();
            System.out.println("Enter To Station: ");
            String toStation = sc.nextLine().trim();
            if(fromStation.equals(toStation)){
                System.out.println("Please enter a different fromstation or toStation");
                return;
            }

            if (bookingSystem.findTrain(fromStation, toStation)) {
                CoachDetails coachDetails = new CoachDetails();
                coachDetails.coachBooking(bookingSystem);
            } else {
                System.out.println("No train found for the given stations.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}