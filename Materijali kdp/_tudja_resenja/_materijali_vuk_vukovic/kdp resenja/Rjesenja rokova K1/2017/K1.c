/*K1 23.03.2017*/

//1.
const int N=3;
int turn[3]= {0};
void process[i](){
	while(true){
		turn[i]=1;
		turn[i]=max(turn[1:3])+1;
		for(for j=1 to N){
			if(j==i) continue;
			while(turn[j]!=0 && (turn[i],i)>(turn[j],j))skip;
			/*...CS...*/
			turn[i]=0;
		}
	}
}

//Procesi ulaze redom: P1,P2,P3

//2.

void init(){
	//sem mutex[4]={1};
	sem cards[4]={2};
	bool end=false;
}

void player(int id){
	while(!end){

		if(allFour()){
			end=true;
			break;
		}

		signal(cards[i]);
		wait(cars[(i+1)%4]);
	}
}







