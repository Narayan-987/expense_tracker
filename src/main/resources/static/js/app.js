const API_BASE = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", () => {



    const expenseForm =
        document.getElementById("expenseForm");

    if (expenseForm) {

        expenseForm.addEventListener(
            "submit",
            async (e) => {

                e.preventDefault();

                const expenseId =
                    document.getElementById(
                        "expenseId"
                    ).value;

                const data = {

                    description:
                        document.getElementById(
                            "description"
                        ).value,

                    amount:
                        document.getElementById(
                            "amount"
                        ).value,

                    date:
                        document.getElementById(
                            "date"
                        ).value,

                    categoryId:
                        document.getElementById(
                            "categoryId"
                        ).value
                };

                let url =
                    `${API_BASE}/expenses`;

                let method = "POST";

                if (expenseId) {

                    url =
                        `${API_BASE}/expenses/${expenseId}`;

                    method = "PUT";
                }

                try {

                    const response =
                        await fetch(url, {

                            method: method,

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body: JSON.stringify(data)
                        });

                    if (response.ok) {

                        location.reload();

                    } else {

                        const errorData =
                            await response.json();

                        alert(
                            errorData.error ||

                            errorData.message ||

                            Object.values(
                                errorData.errors || {}
                            ).join("\n")
                        );
                    }

                } catch (error) {

                    alert(
                        "Server error occurred"
                    );
                }
            }
        );
    }



    const categoryForm =
        document.getElementById(
            "categoryForm"
        );

    if (categoryForm) {

        categoryForm.addEventListener(
            "submit",
            async (e) => {

                e.preventDefault();

                const data = {

                    name:
                        document.getElementById(
                            "categoryName"
                        ).value
                };

                try {

                    const response =
                        await fetch(
                            `${API_BASE}/categories`,
                            {

                                method: "POST",

                                headers: {
                                    "Content-Type":
                                        "application/json"
                                },

                                body: JSON.stringify(data)
                            }
                        );

                    if (response.ok) {

                        location.reload();

                    } else {

                        const errorData =
                            await response.json();

                        alert(
                            errorData.error ||

                            errorData.message ||

                            Object.values(
                                errorData.errors || {}
                            ).join("\n")
                        );
                    }

                } catch (error) {

                    alert(
                        "Server error occurred"
                    );
                }
            }
        );
    }
});




function editExpense(
    id,
    description,
    amount,
    date,
    categoryId
) {

    document.getElementById(
        "expenseId"
    ).value = id;

    document.getElementById(
        "description"
    ).value = description;

    document.getElementById(
        "amount"
    ).value = amount;

    document.getElementById(
        "date"
    ).value = date;

    document.getElementById(
        "categoryId"
    ).value = categoryId;

    document.getElementById(
        "saveBtn"
    ).innerText = "Update Expense";
}




async function deleteExpense(id) {

    if (!confirm(
        "Delete this expense?"
    )) {
        return;
    }

    try {

        const response =
            await fetch(
                `${API_BASE}/expenses/${id}`,
                {
                    method: "DELETE"
                }
            );

        if (response.ok) {

            location.reload();

        } else {

            const errorData =
                await response.json();

            alert(
                errorData.error ||
                errorData.message
            );
        }

    } catch (error) {

        alert(
            "Server error occurred"
        );
    }

}

async function deleteCategory(id) {

    if (!confirm("Delete this category?")) {
        return;
    }

    try {

        const response =
            await fetch(
                `${API_BASE}/categories/${id}`,
                {
                    method: "DELETE"
                }
            );

        if (response.ok) {

            location.reload();

        } else {

            const errorData =
                await response.json();

            alert(
                errorData.error ||
                errorData.message
            );
        }

    } catch (error) {

        alert("Server error occurred");
    }
}

async function loadSummary() {

    const month =
        document.getElementById("month").value;

    if (!month) {

        alert("Please select month");

        return;
    }

    try {

        const response =
            await fetch(
                `${API_BASE}/expenses/summary?month=${month}`
            );

        const data =
            await response.json();

        if (!response.ok) {

            alert(
                data.error ||
                data.message
            );

            return;
        }

        let html = `

            <div class="card shadow p-3">

                <h4 class="mb-3 text-success">
                    Overall Total: ₹${data.total}
                </h4>

                <table class="table table-bordered table-striped">

                    <thead class="table-dark">

                        <tr>
                            <th>Category</th>
                            <th>Total</th>
                        </tr>

                    </thead>

                    <tbody>
        `;

        data.categories.forEach(category => {

            html += `

                <tr>

                    <td>${category.category}</td>

                    <td>₹${category.total}</td>

                </tr>
            `;
        });

        html += `

                    </tbody>

                </table>

            </div>
        `;

        document.getElementById(
            "summaryResult"
        ).innerHTML = html;

    } catch (error) {

        alert("Server error occurred");
    }
}

    async function findExpenseById() {

        const id =
            document.getElementById(
                "searchExpenseId"
            ).value;

        if (!id) {

            alert("Enter expense id");

            return;
        }

        try {

            const response =
                await fetch(
                    `${API_BASE}/expenses/${id}`
                );

            if (!response.ok) {

                const errorData =
                    await response.json();

                alert(
                    errorData.message
                );

                return;
            }

            const expense =
                await response.json();

            document.getElementById(
                "expenseSearchResult"
            ).innerHTML = `

                <div class="alert alert-success">

                    <h5>Expense Found</h5>

                    <p>
                        <strong>Description:</strong>
                        ${expense.description}
                    </p>

                    <p>
                        <strong>Amount:</strong>
                        ₹${expense.amount}
                    </p>

                    <p>
                        <strong>Date:</strong>
                        ${expense.date}
                    </p>

                    <p>
                        <strong>Category:</strong>
                        ${expense.categoryName}
                    </p>

                </div>
            `;

        } catch (error) {

            alert("Server error occurred");
        }
    }

    async function findCategoryById() {

        const id =
            document.getElementById(
                "searchCategoryId"
            ).value;

        if (!id) {

            alert("Enter category id");

            return;
        }

        try {

            const response =
                await fetch(
                    `${API_BASE}/categories/${id}`
                );

            if (!response.ok) {

                const errorData =
                    await response.json();

                alert(
                    errorData.message
                );

                return;
            }

            const category =
                await response.json();

            document.getElementById(
                "categorySearchResult"
            ).innerHTML = `

                <div class="alert alert-success">

                    <h5>Category Found</h5>

                    <p>
                        <strong>ID:</strong>
                        ${category.id}
                    </p>

                    <p>
                        <strong>Name:</strong>
                        ${category.name}
                    </p>

                </div>
            `;

        } catch (error) {

            alert("Server error occurred");
        }
    }