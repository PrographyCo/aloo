<?php
namespace App\DataTables\Admin;

use App\Models\About;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class AboutDataTable extends DataTable
{

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->addColumn('action', static function ($query){
                return '<a href="#" onclick=\'editAbout('.json_encode($query).')\' class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';
            })->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })->rawColumns([
                'action',
                'img',
            ]);
    }

    public function query(About $model)
    {
        return $model
            ->newQuery();
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('about-table')
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
            Column::make('data')->title(__('web/admin/datatable.clientService.userType')),
            Column::make('link')->title(__('web/admin/datatable.clientService.type')),
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
