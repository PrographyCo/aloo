<?php
namespace App\DataTables\Vendor;

use App\Http\Controllers\Controller;
use App\Models\Extra;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class ExtraDataTable extends DataTable
{

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('calories', static function ($query){
                return $query->calories . ' ' . __('web/vendor/datatable.extra.calories');
            })
            ->addColumn('action', static function ($query){
                return '<a href="'. route('vendor.extra.edit' ,$query) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';
            })->rawColumns([
                'action',
            ]);
    }


    public function query(Extra $model)
    {
        return $model
            ->newQuery()
            ->where('vendor_id', \Auth::id());
    }


    public function html()
    {
        return $this->builder()
            ->setTableId('extra-table')
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
            Column::make('name_'.Controller::$lang)->title(__('web/vendor/datatable.extra.name')),
            Column::make('price')->title(__('web/vendor/datatable.extra.price')),
            Column::make('calories')->title(__('web/vendor/datatable.extra.calories')),
            Column::make('created_at')->title(__('web/vendor/datatable.extra.created_at')),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(60)
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
        return 'extra_' . date('YmdHis');
    }
}
