import java.util.*;
class HashDict {
    int[][] hashDict = new int[7][1];
    HashDict() {
        for(int i=0;i<7;i++){
            hashDict[i][0] = 0;
        }
    }
    public void insert(int num){
        int index = num%7;
        if(hashDict[index][0]==0){
            hashDict[index][0]=num;
        } else {
            int i=index;
            while(i < 7 && hashDict[i][0]!=0){
                i++;
            }
            if(i!=7){
                hashDict[i][0]=num;
            } else {
                System.out.println("No empty spaces available for "+num+", so inserting at "+index+" position.");
                hashDict[index][0]=num;
            }
        }
    }
    public void deleteValue(int value){
        int flag=0;
        for(int i=0;i<7;i++) {
            if(hashDict[i][0]==value){
                flag=1;
                System.out.println("Deleting "+value+" at "+i+" position");
                hashDict[i][0]=0;
                break;
            }
        }
        if(flag==0){
            System.out.println("The value "+value+" does not exist.");
        }
    }
    public void deleteKey(int key){
        if(hashDict[key][0]!=0){
            System.out.println("Deleted the value "+hashDict[key][0]+" at key "+key);
            hashDict[key][0]=0;
        } else {
            System.out.println("The key "+key+" does not exist.");
        }
    }
    public void printHashTable(){
        for(int i=0;i<7;i++){
            if(hashDict[i][0]!=0){
                System.out.println(i+": "+hashDict[i][0]);
            }
        }
    }
    public void searchValue(int val){
        int flag=0;
        for(int i=0;i<7;i++){
            if(hashDict[i][0]==val){
                flag=1;
                System.out.println(val+" found at "+i);
                break;
            }
        }
        if(flag==0){
            System.out.println("The value "+val+" does not exist.");
        }
    }
    public void searchKey(int key){
        if(hashDict[key][0]!=0){
            System.out.println("At "+key+" the value is "+hashDict[key][0]);
        } else {
            System.out.println("The key "+key+": does not exist");
        }
    }
    public static void main(String[] args) {
        int opt;
        Scanner sc = new Scanner(System.in);
        HashDict ob = new HashDict();
        System.out.println("Enter the number of elements to be inserted");
        opt = sc.nextInt();
        while(opt!=0){
            ob.insert(sc.nextInt());
            opt--;
        }
        ob.printHashTable();
        System.out.println("\nDelete a value");
        ob.deleteValue(sc.nextInt());
        System.out.println("\nDelete a Key");
        ob.deleteKey(sc.nextInt());
        ob.printHashTable();
        System.out.print("Enter value to be searched");
        ob.searchValue(sc.nextInt());
        sc.close();
    }
}
