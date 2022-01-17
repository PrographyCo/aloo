<?php
namespace App\DataTables\Vendor;

use App\Models\Branch;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class BranchDataTable extends DataTable
{
    public function __construct()
    {
        Branch::$show = true;
    }


    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->addColumn('action', static function ($query){
                return '<a href="'. route('vendor.branch.show' ,$query) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                    '<a href="'. route('vendor.branch.edit' ,$query) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>'.
                    '<a href="#" onclick=\'sendMoney('.$query->id.','.($query->wallet->amount).')\' class="rounded mx-1" style="color: green"><i class="ri-money-dollar-circle-fill ri-xl"></i></a>';
            })->rawColumns([
                'action',
                'img',
            ]);
    }


    public function query(Branch $model)
    {
        return $model
            ->newQuery()
            ->where('vendor_id', \Auth::id())
            ->with('wallet');
    }


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


    protected function getColumns()
    {
        return [
            Column::make('id'),
            Column::make('name')->title(__('web/vendor/datatable.branch.name')),
            Column::make('login_number')->title(__('web/vendor/datatable.branch.username')),
            Column::make('available_status')->title(__('web/vendor/datatable.branch.available_status')),
            Column::make('managerPhone')->title(__('web/vendor/datatable.branch.phone')),
            Column::make('managerEmail')->title(__('web/vendor/datatable.branch.email')),
            Column::make('manager')->title(__('web/vendor/datatable.branch.manager')),
            Column::make('managerPosition')->title(__('web/vendor/datatable.branch.managerPosition')),
            Column::make('wallet.amount')->title('Money'),
            Column::make('created_at')->title(__('web/vendor/datatable.branch.created_at')),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(60)
                ->addClass('text-center'),
        ];
    }


    protected function filename()
    {
        return 'branches_' . date('YmdHis');
    }
}
