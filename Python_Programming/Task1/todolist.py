import json

class Task:
    def __init__(self, task_id, title, description, status='Pending'):
        self.task_id = task_id
        self.title = title
        self.description = description
        self.status = status

    def update_task(self, title=None, description=None):
        if title:
            self.title = title
        if description:
            self.description = description

    def mark_as_complete(self):
        self.status = 'Completed'

class ToDoList:
    def __init__(self):
        self.tasks = []

    def add_task(self, title, description):
        task_id = len(self.tasks) + 1
        new_task = Task(task_id, title, description)
        self.tasks.append(new_task)

    def view_tasks(self):
        for task in self.tasks:
            print(f"{task.task_id}. {task.title} - {task.status}\n{task.description}\n")

    def update_task(self, task_id, title=None, description=None):
        for task in self.tasks:
            if task.task_id == task_id:
                task.update_task(title, description)
                return
        print("Task not found.")

    def delete_task(self, task_id):
        self.tasks = [task for task in self.tasks if task.task_id != task_id]

    def mark_task_complete(self, task_id):
        for task in self.tasks:
            if task.task_id == task_id:
                task.mark_as_complete()
                return
        print("Task not found.")

    def save_to_file(self, filename):
        tasks_data = [{'task_id': task.task_id, 'title': task.title, 'description': task.description, 'status': task.status} for task in self.tasks]
        with open(filename, 'w') as file:
            json.dump(tasks_data, file)

    def load_from_file(self, filename):
        try:
            with open(filename, 'r') as file:
                tasks_data = json.load(file)
                self.tasks = [Task(**task) for task in tasks_data]
        except FileNotFoundError:
            print("File not found.")

def main():
    todo_list = ToDoList()
    todo_list.load_from_file('tasks.json')

    while True:
        print("\nTo-Do List:")
        print("1. Add Task")
        print("2. View Tasks")
        print("3. Update Task")
        print("4. Delete Task")
        print("5. Mark Task as Complete")
        print("6. Save and Exit")
        choice = input("Enter your choice: ")

        if choice == '1':
            title = input("Enter task title: ")
            description = input("Enter task description: ")
            todo_list.add_task(title, description)
        elif choice == '2':
            todo_list.view_tasks()
        elif choice == '3':
            task_id = int(input("Enter task ID to update: "))
            title = input("Enter new title (leave blank to keep current): ")
            description = input("Enter new description (leave blank to keep current): ")
            todo_list.update_task(task_id, title, description)
        elif choice == '4':
            task_id = int(input("Enter task ID to delete: "))
            todo_list.delete_task(task_id)
        elif choice == '5':
            task_id = int(input("Enter task ID to mark as complete: "))
            todo_list.mark_task_complete(task_id)
        elif choice == '6':
            todo_list.save_to_file('tasks.json')
            break
        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()
