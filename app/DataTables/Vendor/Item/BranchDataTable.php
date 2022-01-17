<?php
namespace App\DataTables\Vendor\Item;

use App\Models\Branch;
use App\Models\Item;
use Carbon\Carbon;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class BranchDataTable extends DataTable
{
    protected Item $item;
    public function __construct(Item $item)
    {
        $this->item = $item;
        Branch::$show = true;
    }


    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->addColumn('action', function ($query) {
                $html = '<a href="'. route('vendor.branch.show' ,$query) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                '<a href="'. route('vendor.branch.edit' ,$query) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';

                if (\Auth::user()->isRestaurant()){
                    return $html . "<div class='form-check form-switch checkbox-input'><input type='checkbox' class='form-check-input item-input-amount' data-ajax='".route('vendor.branch.changeAmount', [$query->branch_id, $query->item_id])."' data-id='$query->id' ".($query->amount == -1 ? "checked" : "")."></div>";
                }
                return $html."<div class='checkbox-input'><input type='number' class='form-control' data-ajax='".route('vendor.branch.changeAmount', [$query->branch_id, $query->item_id])."' data-id='$query->id' value='$query->amount' /></div>";

            })->rawColumns([
                'action',
                'img',
            ]);
    }

    /**
     * Get query source of dataTable.
     *
     * @param \App\User $model
     * @return BelongsToMany
     */
    public function query(): BelongsToMany
    {
        return $this->item->branches();
    }

    /**
     * Optional method if you want to use html builder.
     *
     * @return \Yajra\DataTables\Html\Builder
     */
    public function html()
    {
        return $this->builder()
            ->setTableId('branch-table')
            ->columns($this->getColumns())
            ->minifiedAjax()
//            ->dom('Bfrtip')
            ->orderBy(1)
            ->buttons(
                Button::make('create'),
                Button::make('export'),
                Button::make('print'),
                Button::make('reset'),
                Button::make('reload')
            );
    }

    /**
     * Get columns.
     *
     * @return array
     */
    protected function getColumns()
    {
        return [
            Column::make('id'),
            Column::make('name')->title(__('web/vendor/datatable.branch.name')),
            Column::make('managerPhone')->title(__('web/vendor/datatable.branch.phone')),
            Column::make('managerEmail')->title(__('web/vendor/datatable.branch.email')),
            Column::make('manager')->title(__('web/vendor/datatable.branch.manager')),
            Column::make('managerPosition')->title(__('web/vendor/datatable.branch.managerPosition')),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(80)
                ->addClass('text-center'),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'branches_' . date('YmdHis');
    }
}
