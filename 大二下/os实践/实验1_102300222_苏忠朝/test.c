#include<stdio.h>
#include<sys/types.h>
#include<unistd.h>
#include<stdlib.h>
int is_prime(int n) {
	if (n < 2)return 0;
	for (int i = 2; i * i <= n; i++) {
		if (n % i == 0)return 0;
	}
	return 1;
}
int primes[20];
int sums[5];
void find_prime() {
	int num = 0;
	int i = 0;
	while (1) {
		i++;
		if (is_prime(i))primes[num++] = i;
		if (num == 20)break;   
	}
}
int main(){
	find_prime();

	pid_t p[5];
	for (int i = 0; i < 5; i++) {
		p[i] = vfork();
		if (p[i] == 0) {
			//子进程
			int start = i * 4;
			int end = start + 4;//一共20个素数，5个进程，每个进程4个
			int sum = 0;
			for (int j = start; j < end; j++) {
				sum += primes[j];
			}
			sums[i] = sum;
			printf("进程%d的总和为%d\n", i, sums[i]);
			_exit(0);//子进程退出 如果用exit(0)父进程也会退出
		}
		else if (p[i] < 0) {
			perror("Fork failed");
			exit(1);//0正常结束  1异常中止
		}

	}
	
	//父进程
	int total = 0;
	for (int i = 0; i < 5; i++) {
		wait(NULL);//等待任意一个子进程结束
		total += sums[i];
	}
	printf("前20个素数的总和为:%d\n", total);
}