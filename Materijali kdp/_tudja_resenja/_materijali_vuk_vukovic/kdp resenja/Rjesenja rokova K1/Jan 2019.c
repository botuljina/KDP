sem mutex = 1;
int states[] = {0,0,0,0,0} 		//0- not hungry, 1- hungry, 2- eating
sem batons[] = {0,0,0,0,0}; 
int orders[] = {0,0,0,0,0};		//govori nam o redosledu dolaska semafora

void philosopher(int id){

	left= (id == 0) ? 4 : id-1;	//moj lijevi i desni filozof
	right = (id + 1) % 5;

	wait(mutex);
	states[id] = 1;
	
	if(states[left] == 2 || states[right] == 2){	//oko mene jedu, tako da je ne mogu 
		orders[id] = max(orders) + 1;
		signal(mutex);
		wait(batons[id]);													
	}
	
	states[id] = 2;
	signal(mutex);
	
	eat();
	
	wait(mutex);
	
	states[id] = 0; 
	int temp= (left == 0) ? 4 : (left - 1);																
	
	if((states[(right +1) % 5] == 2 || orders[right] == 0) && (states[temp] == 2 || orders[left] == 0)){
		signal(mutex);
	}else{
		
		if(orders[left] > orders[right]){							//levi je dosao kasnije
			if(states[(right +1) % 5] != 2 && orders[right] != 0){			//ako desni od desnog ne jede i desni hoce da jede, pusti ga da jede
				orders[right] = 0 ;
				signal(batons[right]);
			}
			
			if(states[temp] != 2 && orders[left] != 0){						//ako leve od levog ne jede i levi hoce da jede, pusti ga da jede
				orders[left] = 0 ;
				signal(batons[left]);
			}
		}else{
			
			int temp= (left==0) ? 4 : (left-1);
			
			if(states[temp] != 2 && orders[temp] != 0){			//ako leve od levog ne jede i levi hoce da jede, pusti ga da jede
				orders[left] = 0 ;
				signal(batons[left]);
			}
			
			if(states[(right +1) % 5] != 2 && orders[right] != 0){			//ako desni od desnog ne jede i desni hoce da jede, pusti ga da jede
				orders[right] = 0 ;
				signal(batons[right]);
			}
		}	
	}

}