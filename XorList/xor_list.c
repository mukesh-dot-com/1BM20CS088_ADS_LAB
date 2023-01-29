#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
struct Node
{
    int data;
    struct Node *npx;
};

struct Node *XOR(struct Node *a, struct Node *b)
{
    return (struct Node *)((uintptr_t)(a) ^ (uintptr_t)(b));
}

void insert(struct Node **head, int data)
{
    struct Node *newNode = (struct Node *)malloc(sizeof(struct Node));
    newNode->data = data;
    newNode->npx = XOR(*head, NULL);

    if (*head != NULL)
    {
        struct Node *next = XOR((*head)->npx, NULL);
        (*head)->npx = XOR(newNode, next);
    }

    *head = newNode;
}

void printList(struct Node *head)
{
    struct Node *curr = head;
    struct Node *prev = NULL;
    struct Node *next;

    printf("Elements of XOR Linked List: ");
    while (curr != NULL)
    {
        printf("%d ", curr->data);

        next = XOR(prev, curr->npx);
        prev = curr;
        curr = next;
    }
}

int main()
{
    int ch,ele;
    struct Node *head = NULL;
    while(1){
        printf("\n1.Insert\t2.Display\t3.exit\n");
        scanf("%d",&ch);
        switch(ch){
            case 1: printf("\nEnter element to insert");
                    scanf("%d",&ele);
                    insert(&head,ele);
                    break;
            case 2:printList(head);
                   break;
            case 3:exit(0);
        }
    }
    return 0;
}
