import java.util.*;

class passenger {
    int ticketno;
    String name;
    int age;
    String gender;
    String berth;

    passenger(int ticketno, String name, int age, String gender, String berth) {
        this.ticketno = ticketno;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.berth = berth;
    }

    @Override
    public String toString() {
        return "Ticket no " + ticketno + " Name: " + name + ", Age: " + age + ", Gender: " + gender + ", Alloted Berth: " + berth;
    }
}

class RailwayBookingSystem {
    static int upper = 21;
    static int middle = 21;
    static int lower = 21;
    static int sl = 9;
    static int su = 9;
    static int ticketno = 1;

    public static void main(String[] args) {
        List<passenger> ticket = new LinkedList<>();
        Queue<passenger> rac = new LinkedList<>();
        Queue<passenger> waitinglist = new LinkedList<>();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter 1 ticket booking\nEnter 2 cancellation\nEnter 3 for Available tickets\nEnter 4 for Booked tickets\nEnter 5 to exit");
            int n = sc.nextInt();
            if (n == 5) {
                System.out.println("Exiting");
                break;
            }
            switch (n) {
                case 1:
                    booking(ticket, rac, waitinglist);
                    break;
                case 2:
                    cancellation(ticket, rac, waitinglist);
                    break;
                case 3:
                    availableticket(ticket, rac);
                    break;
                case 4:
                    printingticket(ticket, rac);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    static void cancellation(List<passenger> ticket, Queue<passenger> rac, Queue<passenger> waitinglist) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ticket no");
        int ticketno = sc.nextInt();
        passenger toRemove = null;

        for (passenger obj : ticket) {
            if (obj.ticketno == ticketno) {
                toRemove = obj;
                break;
            }
        }

        if (toRemove != null) {
            ticket.remove(toRemove);
            System.out.println("Your ticket with " + ticketno + " cancelled");

            if (!rac.isEmpty()) {
                passenger fromRac = rac.poll();
                ticket.add(fromRac);
                if (!waitinglist.isEmpty()) {
                    passenger fromWait = waitinglist.poll();
                    rac.add(fromWait);
                }
            }
        } else {
            System.out.println("Ticket not found");
        }
    }

    static void booking(List<passenger> ticket, Queue<passenger> rac, Queue<passenger> waitinglist) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = sc.nextLine();
        System.out.println("Enter your age");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter your gender");
        String gender = sc.nextLine();
        System.out.println("Enter your berth preference (lower/middle/upper/sl/su)");
        String berth = sc.nextLine();

        String alloted = "";

        if (age <=5) {
            ticket.add(new passenger(ticketno++, name, age, gender, "No berth alloted"));
            System.out.println("No ticket booked but details stored");
            return;
        }

        if (age > 60 && lower > 0) {
            alloted = "lower";
            lower--;
        } else if (berth.equalsIgnoreCase("lower") && lower > 0) {
            alloted = "lower";
            lower--;
        } else if (berth.equalsIgnoreCase("middle") && middle > 0) {
            alloted = "middle";
            middle--;
        } else if (berth.equalsIgnoreCase("upper") && upper > 0) {
            alloted = "upper";
            upper--;
        } else if (berth.equalsIgnoreCase("sl") && sl > 0) {
            alloted = "sl";
            sl--;
        } else if (berth.equalsIgnoreCase("su") && su > 0) {
            alloted = "su";
            su--;
        } else if (lower > 0) {
            alloted = "lower";
            lower--;
        } else if (middle > 0) {
            alloted = "middle";
            middle--;
        } else if (upper > 0) {
            alloted = "upper";
            upper--;
        } else if (sl > 0) {
            alloted = "sl";
            sl--;
        } else if (su > 0) {
            alloted = "su";
            su--;
        }

        if (alloted != null && !alloted.isEmpty()) {
            if (ticket.size() < 63) {
                ticket.add(new passenger(ticketno++, name, age, gender, alloted));
                System.out.println("Ticket booked for " + name + " in " + alloted);
            } else if (rac.size() < 18) {
                rac.add(new passenger(ticketno++, name, age, gender, "RAC"));
                System.out.println("RAC Ticket booked for " + name);
            } else if (waitinglist.size() < 10) {
                waitinglist.add(new passenger(ticketno++, name, age, gender, "Waiting List"));
                System.out.println("Added to Waiting List for " + name);
            } else {
                System.out.println("No tickets available");
            }
        }
    }

    static void printingticket(List<passenger> ticket, Queue<passenger> rac) {
        for (passenger p : ticket) {
            System.out.println(p);
        }
        if (!rac.isEmpty()) {
            for (passenger p : rac) {
                System.out.println(p);
            }
        }
    }

    static void availableticket(List<passenger> ticket, Queue<passenger> rac) {
        int x = 63 - ticket.size();
        int y = 18 - rac.size();
        System.out.println(x + " Confirmed tickets available and " + y + " RAC tickets available");
        System.out.println(upper + " upper tickets, " + middle + " middle tickets, " + lower + " lower tickets, " + sl + " sl tickets available, " + su + " su tickets available");
    }
}
