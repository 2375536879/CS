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
			//�ӽ���
			int start = i * 4;
			int end = start + 4;//һ��20��������5�����̣�ÿ������4��
			int sum = 0;
			for (int j = start; j < end; j++) {
				sum += primes[j];
			}
			sums[i] = sum;
			printf("����%d���ܺ�Ϊ%d\n", i, sums[i]);
			_exit(0);//�ӽ����˳� �����exit(0)������Ҳ���˳�
		}
		else if (p[i] < 0) {
			perror("Fork failed");
			exit(1);//0��������  1�쳣��ֹ
		}

	}
	
	//������
	int total = 0;
	for (int i = 0; i < 5; i++) {
		wait(NULL);//�ȴ�����һ���ӽ��̽���
		total += sums[i];
	}
	printf("ǰ20���������ܺ�Ϊ:%d\n", total);
}