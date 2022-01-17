<?php
namespace App\DataTables\Admin;

use App\Models\Bank;
use App\Models\KitchenType;
use App\Models\RestaurantTypes;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class RestaurantTypeDataTable extends DataTable
{

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->addColumn('action', static function ($query){
                return '<a href="#" onclick=\'editKitchen('.json_encode($query).')\' class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';
            })
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            });
    }

    public function query(RestaurantTypes $model)
    {
        return $model
            ->newQuery();
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('kitchen-table')
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
            Column::make('name_en')->title(__('web/admin/datatable.bank.name_en')),
            Column::make('name_ar')->title(__('web/admin/datatable.bank.name_ar')),
            Column::make('created_at')->title(__('web/admin/datatable.bank.created_at')),
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
        return 'restaurant_types_' . date('YmdHis');
    }
}
