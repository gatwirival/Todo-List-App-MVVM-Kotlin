package com.example.todolist.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.database.TodoItem
import com.example.todolist.utilities.convertMillis
import com.example.todolist.utilities.convertNumberToMonthName
import kotlinx.android.synthetic.main.item_todo_list.view.*
import java.util.*

class TodoListAdapter(todoItemClickListener: TodoItemClickListener) :
    RecyclerView.Adapter<TodoListAdapter.ViewHolder>(), Filterable {

    private var todoItemList: List<TodoItem> = arrayListOf()
    private var filteredTodoItemList: List<TodoItem> = arrayListOf()
    private val listener: TodoItemClickListener = todoItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredTodoItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(filteredTodoItemList[position], listener)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()

                filteredTodoItemList = if (charString.isEmpty()) {
                    todoItemList
                } else {
                    val filteredList = arrayListOf<TodoItem>()
                    for (item in todoItemList) {
                        if (item.description?.toLowerCase(Locale.getDefault())!!.contains(
                                charString.toLowerCase(
                                    Locale.getDefault()
                                )
                            )
                            || item.title.toLowerCase(Locale.getDefault()).contains(
                                charString.toLowerCase(
                                    Locale.getDefault()
                                )
                            )
                            || item.tags?.toLowerCase(Locale.getDefault())!!.contains(
                                charString.toLowerCase(
                                    Locale.getDefault()
                                )
                            )
                        ) {
                            filteredList.add(item)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredTodoItemList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredTodoItemList = results?.values as List<TodoItem>
                notifyDataSetChanged()
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(todoItem: TodoItem, listener: TodoItemClickListener) {
            itemView.tv_item_title.text = todoItem.title
            itemView.checkbox_item.isChecked = todoItem.completed

            if (todoItem.completed) {
                // Strike through the text to give an indicator that task is completed.
                itemView.tv_item_title.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                itemView.tv_item_due_date.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                itemView.tv_due_date.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            } else {
                itemView.tv_item_title.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                itemView.tv_item_due_date.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                itemView.tv_due_date.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }

            if (todoItem.dueTime!!.toInt() != 0) {
                val dateValues = convertMillis(todoItem.dueTime)
                val displayFormat: String

                if (dateValues[4] < 10) {
                    displayFormat = String
                        .format(
                            itemView.context.getString(R.string.due_date_minute_less_than_ten),
                            convertNumberToMonthName(dateValues[1]),
                            dateValues[0],
                            dateValues[2],
                            dateValues[3],
                            dateValues[4]
                        )
                } else {
                    displayFormat = String
                        .format(
                            itemView.context.getString(R.string.due_date_minute_greater_than_ten),
                            convertNumberToMonthName(dateValues[1]),
                            dateValues[0],
                            dateValues[2],
                            dateValues[3],
                            dateValues[4]
                        )
                }

                itemView.tv_item_due_date.text = displayFormat
            } else {
                itemView.tv_item_due_date.text =
                    itemView.context.getString(R.string.no_due_is_set)
            }

            itemView.setOnClickListener {
                listener.onItemClicked(todoItem)
            }

            itemView.checkbox_item.setOnClickListener {
                listener.onCheckClicked(todoItem)
            }

            itemView.iv_delete_item.setOnClickListener {
                listener.onDeleteClicked(todoItem)
            }
        }
    }

    fun setTodoItems(todoItems: List<TodoItem>) {
        this.todoItemList = todoItems
        this.filteredTodoItemList = todoItems
        notifyDataSetChanged()
    }

    interface TodoItemClickListener {
        fun onDeleteClicked(todoItem: TodoItem)
        fun onItemClicked(todoItem: TodoItem)
        fun onCheckClicked(todoItem: TodoItem)
    }
}