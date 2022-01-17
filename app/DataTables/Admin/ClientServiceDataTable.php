<?php
namespace App\DataTables\Admin;

use App\Helpers\APIHelper;
use App\Models\ClientService;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class ClientServiceDataTable extends DataTable
{

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->addColumn('action', static function ($query){
                return '<a href="mailto:'.$query->email.'" class="rounded mx-1"><i class="ri-reply-fill"></i></a>';
            })->addColumn('img', static function ($query){
                if ($query->img!==null)
                    return '<a href="'.APIHelper::getImageUrl($query->img,'clientService').'">Show Image</a>';
                return null;
            })->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })->rawColumns([
                'action',
                'img',
            ]);
    }

    public function query(ClientService $model)
    {
        return $model
            ->newQuery();
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('clientService-table')
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
            Column::make('userType')->title(__('web/admin/datatable.clientService.userType')),
            Column::make('type')->title(__('web/admin/datatable.clientService.type')),
            Column::make('question')->title(__('web/admin/datatable.clientService.question')),
            Column::make('email')->title(__('web/admin/datatable.clientService.email')),
            Column::computed('img')->title(__('web/admin/datatable.clientService.img')),
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
